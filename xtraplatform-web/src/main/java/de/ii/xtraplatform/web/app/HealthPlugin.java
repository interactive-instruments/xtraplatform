/*
 * Copyright 2015-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.app;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.github.azahnen.dagger.annotations.AutoBind;
import de.ii.xtraplatform.base.domain.AppConfiguration;
import de.ii.xtraplatform.base.domain.resiliency.HealthChecks;
import de.ii.xtraplatform.base.domain.resiliency.Volatile2;
import de.ii.xtraplatform.base.domain.resiliency.VolatileRegistry;
import de.ii.xtraplatform.web.domain.DropwizardPlugin;
import io.dropwizard.core.setup.Environment;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zahnen
 */
@Singleton
@AutoBind
public class HealthPlugin implements DropwizardPlugin, HealthChecks {

  private static final Logger LOGGER = LoggerFactory.getLogger(HealthPlugin.class);

  private final VolatileRegistry volatileRegistry;
  private HealthCheckRegistry healthCheckRegistry;

  @Inject
  public HealthPlugin(VolatileRegistry volatileRegistry) {
    this.volatileRegistry = volatileRegistry;
  }

  @Override
  public void init(AppConfiguration configuration, Environment environment) {
    this.healthCheckRegistry = environment.healthChecks();
    healthCheckRegistry.unregister("deadlocks");
    volatileRegistry.listen(this::register, this::unregister);
  }

  // @Override
  public void register(String name, HealthCheck check) {
    if (!healthCheckRegistry.getNames().contains(name)) {
      healthCheckRegistry.register(name, check);
    }
  }

  private void register(String name, Volatile2 volatile2) {
    volatile2.asHealthCheck().ifPresent(check -> register(name, check));
  }

  public void unregister(String name) {
    healthCheckRegistry.unregister(name);
  }
}
