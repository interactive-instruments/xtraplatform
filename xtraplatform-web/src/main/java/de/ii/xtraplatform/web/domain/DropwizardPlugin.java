/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.domain;

import com.github.azahnen.dagger.annotations.AutoMultiBind;
import de.ii.xtraplatform.base.domain.AppConfiguration;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

@AutoMultiBind
public interface DropwizardPlugin {

  default int getPriority() {
    return 1000;
  }

  default void initBootstrap(Bootstrap<AppConfiguration> bootstrap) {}

  void init(AppConfiguration configuration, Environment environment);
}
