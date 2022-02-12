/*
 * Copyright 2015-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.logging.LoggingFactory;
import io.dropwizard.server.ServerFactory;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class XtraPlatformConfiguration extends Configuration {

  public XtraPlatformConfiguration() {}

  @Valid @NotNull private XtraPlatformServerFactory server = new XtraPlatformServerFactory();
  @Valid @Nullable private XtraPlatformLoggingFactory logging;

  @Override
  @JsonProperty("server")
  public XtraPlatformServerFactory getServerFactory() {
    return server;
  }

  @Override
  @JsonIgnore
  public void setServerFactory(ServerFactory factory) {}

  @JsonProperty("server")
  public void setServerFactory(XtraPlatformServerFactory factory) {
    this.server = factory;
  }

  @Override
  @JsonProperty("logging")
  public synchronized LoggingFactory getLoggingFactory() {
    return logging;
  }

  @Override
  @JsonIgnore
  public synchronized void setLoggingFactory(LoggingFactory factory) {}

  @JsonProperty("logging")
  public synchronized void setLoggingFactory(XtraPlatformLoggingFactory factory) {
    this.logging = factory;
  }

  // TODO: not used anymore, but removing breaks backwards compatibility
  @Deprecated @JsonProperty public boolean useFormattedJsonOutput;

  @Deprecated @JsonProperty public boolean allowServiceReAdding;

  /*
  @JsonProperty
  public String externalURL;

  @JsonProperty
  public int maxDebugLogDurationMinutes = 60;
  */
  @Valid @NotNull private HttpClientConfiguration httpClient;

  @JsonProperty("httpClient")
  public HttpClientConfiguration getHttpClient() {
    return httpClient;
  }

  @JsonProperty("httpClient")
  public void setHttpClient(HttpClientConfiguration httpClient) {
    this.httpClient = httpClient;
  }

  @Valid @NotNull @JsonProperty public StoreConfiguration store = new StoreConfiguration();

  @Valid @NotNull @JsonProperty public AuthConfig auth = new AuthConfig();

  @Valid @NotNull @JsonProperty public ManagerConfiguration manager = new ManagerConfiguration();

  @Valid @NotNull @JsonProperty
  public BackgroundTasksConfiguration backgroundTasks = new BackgroundTasksConfiguration();

  @Valid @NotNull @JsonProperty public ProjConfiguration proj = new ProjConfiguration();

  @Valid @JsonProperty public ClusterConfiguration cluster;
}
