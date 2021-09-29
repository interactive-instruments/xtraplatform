/*
 * Copyright 2015-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.dropwizard.app;

import de.ii.xtraplatform.dropwizard.app.amdatu.DefaultPageParser;
import de.ii.xtraplatform.dropwizard.app.amdatu.DefaultPages;
import de.ii.xtraplatform.dropwizard.app.amdatu.InvalidEntryException;
import de.ii.xtraplatform.dropwizard.app.amdatu.ResourceEntry;
import de.ii.xtraplatform.dropwizard.app.amdatu.ResourceKeyParser;
import de.ii.xtraplatform.dropwizard.domain.StaticResourceHandler;
import de.ii.xtraplatform.dropwizard.domain.StaticResourceServlet;
import de.ii.xtraplatform.runtime.domain.LogContext.MARKER;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.extender.Extender;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author zahnen */
@Component
@Provides
@Instantiate
@Extender(
    onArrival = "onBundleArrival",
    onDeparture = "onBundleDeparture",
    extension = StaticResourceConstants.WEB_RESOURCE_KEY)
public class StaticResourceRegistry implements StaticResourceHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceRegistry.class);

  private final BundleContext bundleContext;
  private final Map<Long, List<ServiceRegistration>> servlets;
  private final Map<String, Servlet> servlets2;

  public StaticResourceRegistry(@Context BundleContext bundleContext) {
    this.bundleContext = bundleContext;
    this.servlets = new ConcurrentHashMap<>();
    this.servlets2 = new ConcurrentHashMap<>();
  }

  @Override
  public boolean handle(String path, HttpServletRequest request, HttpServletResponse response) {
    for (String prefix : servlets2.keySet()) {
      if (path.startsWith(prefix) || ("/" + path).startsWith(prefix)) {
        try {
          servlets2.get(prefix).service(request, response);
          return true;
        } catch (ServletException | IOException e) {
          return false;
        }
      }
    }

    return false;
  }

  private synchronized void onBundleArrival(Bundle bundle, String header) {
    try {
      Map<String, ResourceEntry> entryMap = ResourceKeyParser.getEntries(header);
      DefaultPages defaultPages =
          DefaultPageParser.parseDefaultPages(
              bundle.getHeaders().get(StaticResourceConstants.WEB_RESOURCE_DEFAULT_PAGE));
      Optional<String> rootRedirect =
          Optional.ofNullable(
              bundle.getHeaders().get(StaticResourceConstants.WEB_RESOURCE_ROOT_REDIRECT));

      List<ServiceRegistration> serviceRegistrations = new ArrayList<>();

      for (ResourceEntry entry : entryMap.values()) {
        if (LOGGER.isDebugEnabled(MARKER.DI))
          LOGGER.debug(
              MARKER.DI,
              "Registered static web resource: {} {} {}",
              entry.getPaths().get(0),
              entry.getAlias(),
              defaultPages.getDefaultPageFor(entry.getPaths().get(0)));

        HttpServlet staticResourceServlet =
            new StaticResourceServlet(
                entry.getPaths().get(0),
                entry.getAlias(),
                StandardCharsets.UTF_8,
                bundle,
                defaultPages,
                rootRedirect);
        Hashtable<String, Object> props = new Hashtable<>();
        props.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, entry.getAlias());
        props.put(
            HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
            "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=org.osgi.service.http)");

        ServiceRegistration serviceRegistration =
            bundleContext.registerService(Servlet.class.getName(), staticResourceServlet, props);
        serviceRegistrations.add(serviceRegistration);

        servlets2.put(entry.getAlias(), staticResourceServlet);
      }
      servlets.put(bundle.getBundleId(), serviceRegistrations);

    } catch (InvalidEntryException ex) {
      // LOGGER.info("STATIC", ex);
    }
  }

  private synchronized void onBundleDeparture(Bundle bundle) {
    if (servlets.containsKey(bundle.getBundleId())) {
      for (ServiceRegistration serviceRegistration : servlets.get(bundle.getBundleId())) {
        serviceRegistration.unregister();
      }
    }
  }
}
