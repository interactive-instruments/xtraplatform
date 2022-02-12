/*
 * Copyright 2015-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.app;

import ch.qos.logback.classic.Level;
import ch.qos.logback.core.Appender;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.azahnen.dagger.annotations.AutoBind;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.ii.xtraplatform.web.domain.ApplicationProvider;
import de.ii.xtraplatform.web.domain.Dropwizard;
import de.ii.xtraplatform.web.domain.MustacheResolverRegistry;
import de.ii.xtraplatform.base.domain.Constants.ENV;
import de.ii.xtraplatform.base.domain.Lifecycle;
import de.ii.xtraplatform.base.domain.LogContext;
import de.ii.xtraplatform.base.domain.XtraPlatformConfiguration;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.ViewRenderer;
import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.LoggerFactory;

/** @author zahnen */
@Singleton
@AutoBind
public class DropwizardProvider implements Dropwizard, Lifecycle {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DropwizardProvider.class);

  // Service not published by default
  // @ServiceController(value = false)
  private boolean controller;

  private final ApplicationProvider applicationProvider;
  private final MustacheResolverRegistry mustacheResolverRegistry;
  private final String applicationName;
  private final String applicationVersion;
  private final ENV applicationEnvironment;

  private XtraPlatformConfiguration configuration;
  private Environment environment;
  private ServletContainer jerseyContainer;
  private ViewRenderer mustacheRenderer;
  private Server server;

  @Inject
  public DropwizardProvider(
      ApplicationProvider applicationProvider,
      MustacheResolverRegistry mustacheResolverRegistry) {
    this.applicationProvider = applicationProvider;
    this.mustacheResolverRegistry = mustacheResolverRegistry;
    this.applicationName = "TODO"; // context.getProperty(Constants.APPLICATION_KEY);
    this.applicationVersion = "TODO"; // context.getProperty(Constants.VERSION_KEY);
    this.applicationEnvironment =
        ENV.DEVELOPMENT; // TODO.valueOf(context.getProperty(Constants.ENV_KEY));
  }

  @Override
  public void onStart() {
    Thread.currentThread().setName("startup");

    Path cfgFile =
        Path.of(
            "/home/zahnen/development/configs-ldproxy/inspire-nrw/cfg.yml"); // TODO
                                                                             // Paths.get(context.getProperty(Constants.USER_CONFIG_PATH_KEY));

    try {
      init(cfgFile, applicationEnvironment);

      // publish the service once the initialization
      // is completed.
      controller = true;

      LOGGER.debug("User configurations: [{}]", cfgFile);

      run();

    } catch (Throwable ex) {
      LogContext.error(
          LOGGER, ex, "Error initializing {} with configuration file {}", applicationName, cfgFile);
      System.exit(1);
    }
  }

  @Override
  public void onStop() {
    LOGGER.debug("onStop");
    try {
      server.stop();
      server.join();
    } catch (Exception e) {
      LogContext.error(
          LOGGER, e, "Error when stopping web server.");
    }
  }

  private void run() throws Exception {

    environment.jersey().setUrlPattern(WebServerDropwizard.JERSEY_ENDPOINT);

    this.server = configuration.getServerFactory().build(environment);

    //ServletRegistration.Dynamic servlet = dw.getServlets().addServlet("osgi", new ProxyServlet());
    //servlet.addMapping(APP_ENDPOINT);

    //addAdminEndpoint();

    server.start();

    LOGGER.info("Started web server at {}"/*, getUrl()*/);

  }

  private void init(Path cfgFilePath, ENV env) {
    Pair<XtraPlatformConfiguration, Environment> configurationEnvironmentPair =
        applicationProvider.startWithFile(cfgFilePath, env, this::initBootstrap);

    this.configuration = configurationEnvironmentPair.getLeft();
    this.environment = configurationEnvironmentPair.getRight();
    this.jerseyContainer = (ServletContainer) environment.getJerseyServletContainer();

    // this.environment.healthChecks().register("ModulesHealthCheck", new ModulesHealthCheck());

    // TODO: enable trailing slashes, #36
    // environment.jersey().enable(ResourceConfig.FEATURE_REDIRECT);
    this.environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    // TODO: per parameter
    // if (configuration.useFormattedJsonOutput) {
    environment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    // LOGGER.warn(FrameworkMessages.GLOBALLY_ENABLED_JSON_PRETTY_PRINTING);
    // }

    environment
        .metrics()
        .removeMatching(
            new MetricFilter() {
              @Override
              public boolean matches(String name, Metric metric) {
                if (name.startsWith("jvm.memory.pools") || name.startsWith("ch.qos.logback")) {
                  return true;
                }
                return false;
              }
            });
  }

  // @Override
  private void initBootstrap(Bootstrap<XtraPlatformConfiguration> bootstrap) {
    this.mustacheRenderer = new FallbackMustacheViewRenderer(mustacheResolverRegistry);

    boolean cacheTemplates = !isDevEnv();

    bootstrap.addBundle(
        new ViewBundle<XtraPlatformConfiguration>(ImmutableSet.of(mustacheRenderer)) {
          @Override
          public Map<String, Map<String, String>> getViewConfiguration(
              XtraPlatformConfiguration configuration) {
            return ImmutableMap.of(
                mustacheRenderer.getConfigurationKey(),
                ImmutableMap.of("cache", Boolean.toString(cacheTemplates)));
          }
        });
  }

  @Override
  public String getApplicationName() {
    return applicationName;
  }

  @Override
  public String getApplicationVersion() {
    return applicationVersion;
  }

  @Override
  public ENV getApplicationEnvironment() {
    return applicationEnvironment;
  }

  @Override
  public XtraPlatformConfiguration getConfiguration() {
    return configuration;
  }

  @Override
  public Environment getEnvironment() {
    return environment;
  }

  /*@Override
  public Map<String, Boolean> getFlags() {
      Map<String, Boolean> flags = new HashMap<>();

      flags.put(FLAG_ALLOW_SERVICE_READDING, getConfiguration().allowServiceReAdding);
      flags.put(FLAG_USE_FORMATTED_JSON_OUTPUT, getConfiguration().useFormattedJsonOutput);

      return flags;
  }*/

  @Override
  public ServletEnvironment getServlets() {
    return getEnvironment().servlets();
  }

  @Override
  public ServletContext getServletContext() {
    return getEnvironment().getApplicationContext().getServletContext();
  }

  @Override
  public MutableServletContextHandler getApplicationContext() {
    return getEnvironment().getApplicationContext();
  }

  @Override
  public JerseyEnvironment getJersey() {
    return getEnvironment().jersey();
  }

  @Override
  public ServletContainer getJerseyContainer() {
    return jerseyContainer;
  }

  @Override
  public URI getUri() {
    if (Strings.isNullOrEmpty(configuration.getServerFactory().getExternalUrl())) {
      return URI.create(
          String.format("%s://%s:%d", getScheme(), getHostName(), getApplicationPort()));
    }

    return URI.create(
        configuration
            .getServerFactory()
            .getExternalUrl()
            .replace("rest/services/", "")
            .replace("rest/services", ""));
  }

  @Override
  public URI getServicesUri() {
    if (Strings.isNullOrEmpty(configuration.getServerFactory().getExternalUrl())) {
      return URI.create(
          String.format(
              "%s://%s:%d/rest/services", getScheme(), getHostName(), getApplicationPort()));
    }

    String uri = configuration.getServerFactory().getExternalUrl();
    if (uri.endsWith("/")) {
      uri = uri.substring(0, uri.length() - 1);
    }

    return URI.create(uri);
  }

  private int getApplicationPort() {
    return ((HttpConnectorFactory)
            ((DefaultServerFactory) getConfiguration().getServerFactory())
                .getApplicationConnectors()
                .get(0))
        .getPort();
  }

  private String getScheme() {

    return Optional.ofNullable(configuration.getServerFactory().getExternalUrl())
        .map(URI::create)
        .map(URI::getScheme)
        .orElse("http");
  }

  private String getHostName() {

    return Optional.ofNullable(configuration.getServerFactory().getExternalUrl())
        .map(URI::create)
        .map(URI::getHost)
        .orElse("localhost");

    /*String hostName = "";
    try {
        InetAddress iAddress = InetAddress.getLocalHost();
        hostName = iAddress.getCanonicalHostName();
        if (Objects.isNull(hostName) || hostName.isEmpty()) {
            hostName = iAddress.getHostName();
        }
    } catch (UnknownHostException e) {
        // failed;  try alternate means.
    }
    // windows environment variable
    if (Objects.isNull(hostName) || hostName.isEmpty()) {
        hostName = System.getenv("COMPUTERNAME");
    }
    // linux environment variable
    if (Objects.isNull(hostName) || hostName.isEmpty()) {
        hostName = System.getenv("HOSTNAME");
    }
    // last option
    if (Objects.isNull(hostName) || hostName.isEmpty()) {
        hostName = "localhost";
    }

    return hostName;*/
  }

  /*@Override
  public int getDebugLogMaxMinutes() {
      return getConfiguration().maxDebugLogDurationMinutes;
  }*/

  @Override
  public void attachLoggerAppender(Appender appender) {
    /*if (!appender.isStarted()) {
        appender.setContext(ROOT_LOGGER.getLoggerContext());
        appender.start();
    }
    ROOT_LOGGER.addAppender(appender);*/
  }

  @Override
  public void detachLoggerAppender(Appender appender) {
    // ROOT_LOGGER.detachAppender(appender);
    // appender.stop();
  }

  @Override
  public void setLoggingLevel(Level level) {
    // TODO: this only works for loggers whose level is not set explicitely in config
    // ROOT_LOGGER.setLevel(level);
  }

  @Override
  public void resetServer() {
    // cleanup metrics
    for (String name : environment.metrics().getNames()) {
      if (name.contains("jetty")) {
        environment.metrics().remove(name);
      }
    }

    // cleanup jersey
    // still not sure why this is needed and why it works
    // if we don't do this, we get exceptions from the jersey thread local stack
    // that handles context injections for singleton resource classes
    // the jersey ServletContainer is a thin servlet wrapper around the resource config
    // we create a new instance of ServletContainer and replace the old one in the jetty config
    // BUT: if we destroy the old one and/or replace the reference that JaxRsRegistry uses for
    // reloads,
    // we get exceptions again
    for (ServletHolder sh : getApplicationContext().getServletHandler().getServlets()) {
      if (sh.getName().contains("jersey")) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("JERSEY CLEANUP");
        }
        ServletContainer sc = new ServletContainer(environment.jersey().getResourceConfig());
        sh.setServlet(sc);

        // this.jerseyContainer.reload();
        // this.jerseyContainer.destroy();
        // this.jerseyContainer = sc;
      }
    }
  }

  @Override
  public ViewRenderer getMustacheRenderer() {
    return mustacheRenderer;
  }
}
