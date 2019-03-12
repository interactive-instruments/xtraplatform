/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.auth.external;

import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpCharsets;
import akka.http.javadsl.model.HttpMethods;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.MediaTypes;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.ii.xtraplatform.akka.http.AkkaHttp;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.DefaultUnauthorizedHandler;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import static de.ii.xtraplatform.api.functional.LambdaWithException.mayThrow;

/**
 * @author zahnen
 */
public class ExternalDynamicAuthFilter<P extends Principal> extends AuthFilter<String, P> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalDynamicAuthFilter.class);

    private static final ContentType.WithFixedCharset XACML = ContentTypes.create(MediaTypes.applicationWithFixedCharset("xacml+json", HttpCharsets.UTF_8));
    private static final ContentType.WithFixedCharset GEOJSON = ContentTypes.create(MediaTypes.applicationWithFixedCharset("geo+json", HttpCharsets.UTF_8));
    private static final ObjectMapper JSON = new ObjectMapper();

    private final String edaUrl;
    private final String ppUrl;
    private final AkkaHttp akkaHttp;
    private final OAuthCredentialAuthFilter<P> delegate;


    ExternalDynamicAuthFilter(String edaUrl, String ppUrl, AkkaHttp akkaHttp, OAuthCredentialAuthFilter<P> delegate) {
        super();
        this.realm = "realm";
        this.prefix = "Basic";
        this.unauthorizedHandler = new DefaultUnauthorizedHandler();

        this.edaUrl = edaUrl;
        this.ppUrl = ppUrl;
        this.akkaHttp = akkaHttp;
        this.delegate = delegate;
    }

    //TODO
    static List<String> METHODS = ImmutableList.of("POST", "PUT", "DELETE");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        SecurityContext oldSecurityContext = requestContext.getSecurityContext();

        delegate.filter(requestContext);

        if (METHODS.contains(requestContext.getMethod())) {

            List<String> pathSegments = Splitter.on('/')
                                                .omitEmptyStrings()
                                                .splitToList(requestContext.getUriInfo()
                                                                           .getPath());
            int serviceIndex = pathSegments.indexOf("services") + 1;

            if (serviceIndex > 0 && pathSegments.size() > 1) {
                boolean authorized = isAuthorized(requestContext.getSecurityContext()
                                                                .getUserPrincipal()
                                                                .getName(), requestContext.getMethod(), "/" + Joiner.on('/')
                                                                                                                    .join(pathSegments.subList(serviceIndex + 1, pathSegments.size())), getEntityBody(requestContext));

                if (!authorized) {
                    // reset security context, because we use @PermitAll and then decide based on Principal existence
                    requestContext.setSecurityContext(oldSecurityContext);
                    // is ignored for @PermitAll
                    throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
                }

                if (!ppUrl.isEmpty()) {
                    postProcess(requestContext, getEntityBody(requestContext));
                }
            }
        }
    }

    private void postProcess(ContainerRequestContext requestContext, byte[] body) {
        if (requestContext.getMethod().equals("POST") || requestContext.getMethod().equals("PUT")) {
            try {

                HttpResponse httpResponse = akkaHttp.getResponse(HttpRequest.create(ppUrl)
                                                                            .withMethod(HttpMethods.POST)
                                                                            .withEntity(GEOJSON, body))
                                                    .toCompletableFuture()
                                                    .join();
                if (httpResponse.status()
                                .isSuccess()) {

                    ByteString processed = httpResponse.entity()
                                                  .getDataBytes()
                                                  .runFold(ByteString.empty(), ByteString::concat, akkaHttp.getMaterializer())
                                                  .toCompletableFuture()
                                                  .join();
                    byte[] array = processed.toArray();

                    putEntityBody(requestContext, array);

                }
            } catch (Throwable e) {
                //ignore
                boolean stop = true;
            }
        }
    }

    private boolean isAuthorized(String user, String method, String path, byte[] body) {

        //LOGGER.debug("EDA {} {} {} {}", user, method, path, new String(body, Charset.forName("utf-8")));

        try {

            XacmlRequest xacmlRequest1 = new XacmlRequest(user, method, path, body);
            byte[] xacmlRequest = JSON.writeValueAsBytes(xacmlRequest1);

            //LOGGER.debug("XACML {}", JSON.writerWithDefaultPrettyPrinter()
            //                             .writeValueAsString(xacmlRequest1));

            HttpResponse httpResponse = akkaHttp.getResponse(HttpRequest.create(edaUrl)
                                                                        .withMethod(HttpMethods.POST)
                                                                        .withEntity(XACML, xacmlRequest))
                                                .toCompletableFuture()
                                                .join();
            if (httpResponse.status()
                            .isSuccess()) {

                XacmlResponse xacmlResponse = httpResponse.entity()
                                                          .getDataBytes()
                                                          .runFold(ByteString.empty(), ByteString::concat, akkaHttp.getMaterializer())
                                                          .thenApply(mayThrow(byteString -> (XacmlResponse) JSON.readValue(byteString.utf8String(), XacmlResponse.class)))
                                                          .toCompletableFuture()
                                                          .join();

                return xacmlResponse.isAllowed();
            }
        } catch (Throwable e) {
            //ignore
        }

        return false;
    }

    private byte[] getEntityBody(ContainerRequestContext requestContext) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();

        //final StringBuilder b = new StringBuilder();
        try {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            /*if (requestEntity.length == 0) {
                b.append("")
                 .append("\n");
            } else {
                b.append(new String(requestEntity))
                 .append("\n");
            }*/
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

            return requestEntity;

        } catch (IOException ex) {
            //Handle logging error
        }
        return new byte[0];
    }

    private void putEntityBody(ContainerRequestContext requestContext, byte[] body) {
        requestContext.setEntityStream(new ByteArrayInputStream(body));
    }
}
