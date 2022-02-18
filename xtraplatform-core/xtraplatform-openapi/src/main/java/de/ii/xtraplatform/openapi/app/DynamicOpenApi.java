/*
 * Copyright 2017-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.openapi.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import de.ii.xtraplatform.base.domain.AppLifeCycle;
import de.ii.xtraplatform.web.domain.JaxRsConsumer;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.github.azahnen.dagger.annotations.AutoBind;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AutoBind
public class DynamicOpenApi extends BaseOpenApiResource
    implements DynamicOpenApiChangeListener, AppLifeCycle, JaxRsConsumer {

  private static Logger LOGGER = LoggerFactory.getLogger(DynamicOpenApi.class);
  public static final MediaType YAML_TYPE = new MediaType("application", "yaml");
  public static final String YAML = "application/yaml";
  // private final JaxRsReg registry;

  private OpenAPI openApiSpec;

  @Inject
  public DynamicOpenApi() {
    // this.registry = registry;
  }

  @Override
  public void onStart() {
    // scan();
  }

  @Override
  public Consumer<Set<Object>> getConsumer() {
    return this::scan;
  }

  private synchronized void scan(Set<Object> resources) {
    Set<Class<?>> resourceClasses =
        resources.stream()
            .flatMap(
                resource -> {
                  if (resource instanceof DropwizardResourceConfig.SpecificBinder) {
                    return ((DropwizardResourceConfig.SpecificBinder) resource)
                        .getBindings().stream()
                            .filter(binding -> binding instanceof InstanceBinding)
                            .map(binding -> ((InstanceBinding<?>) binding).getService().getClass());
                  }
                  return Stream.of(resource.getClass());
                })
            .collect(Collectors.toSet());
    Reader reader = new Reader(new OpenAPI());
    this.openApiSpec = reader.read(resourceClasses);
    openApiSpec.addServersItem(new Server().url("/rest"));
    if (Objects.nonNull(openApiSpec.getComponents())) {
      openApiSpec
          .getComponents()
          .addSecuritySchemes(
              "JWT",
              new SecurityScheme()
                  .type(SecurityScheme.Type.HTTP)
                  .scheme("bearer")
                  .bearerFormat("JWT"));
    }
    openApiSpec.addSecurityItem(new SecurityRequirement().addList("JWT"));
  }

  private synchronized Set<Class<?>> getResourceClasses() {
    // TODO
    return new HashSet<>(); // registry.getResources().stream().map(Object::getClass).collect(Collectors.toSet());
  }

  @Override
  public Response getOpenApi(
      HttpHeaders headers, ServletConfig config, Application app, UriInfo uriInfo, String type)
      throws Exception {
    return getOpenApi(headers, uriInfo, type, null);
  }

  @Override
  public Response getOpenApi(
      HttpHeaders headers, UriInfo uriInfo, String type, OpenAPISpecFilter specFilter)
      throws Exception {
    if (openApiSpec == null) {
      return Response.status(404).build();
    }

    OpenAPI oas = openApiSpec;
    boolean pretty = true;

    if (specFilter != null) {
      SpecFilter f = new SpecFilter();
      oas =
          f.filter(
              openApiSpec,
              specFilter,
              getQueryParams(uriInfo.getQueryParameters()),
              getCookies(headers),
              getHeaders(headers));
    }

    if (StringUtils.isNotBlank(type) && type.trim().equalsIgnoreCase("yaml")) {
      return Response.status(Response.Status.OK)
          .entity(pretty ? Yaml.pretty(oas) : Yaml.mapper().writeValueAsString(oas))
          .type("application/yaml")
          .build();
    } else {
      return Response.status(Response.Status.OK)
          .entity(pretty ? Json.pretty(oas) : Json.mapper().writeValueAsString(oas))
          .type(MediaType.APPLICATION_JSON_TYPE)
          .build();
    }
  }

  private static Map<String, List<String>> getQueryParams(MultivaluedMap<String, String> params) {
    Map<String, List<String>> output = new HashMap<String, List<String>>();
    if (params != null) {
      for (String key : params.keySet()) {
        List<String> values = params.get(key);
        output.put(key, values);
      }
    }
    return output;
  }

  private static Map<String, String> getCookies(HttpHeaders headers) {
    Map<String, String> output = new HashMap<String, String>();
    if (headers != null) {
      for (String key : headers.getCookies().keySet()) {
        Cookie cookie = headers.getCookies().get(key);
        output.put(key, cookie.getValue());
      }
    }
    return output;
  }

  private static Map<String, List<String>> getHeaders(HttpHeaders headers) {
    Map<String, List<String>> output = new HashMap<String, List<String>>();
    if (headers != null) {
      for (String key : headers.getRequestHeaders().keySet()) {
        List<String> values = headers.getRequestHeaders().get(key);
        output.put(key, values);
      }
    }
    return output;
  }
}
