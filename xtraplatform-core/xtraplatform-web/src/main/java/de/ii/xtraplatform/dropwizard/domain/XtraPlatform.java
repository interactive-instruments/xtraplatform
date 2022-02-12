/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.dropwizard.domain;

import de.ii.xtraplatform.runtime.domain.Constants.ENV;
import de.ii.xtraplatform.runtime.domain.XtraPlatformConfiguration;
import java.net.URI;

public interface XtraPlatform {

  String getApplicationName();

  String getApplicationVersion();

  ENV getApplicationEnvironment();

  XtraPlatformConfiguration getConfiguration();

  URI getUri();

  URI getServicesUri();

  default boolean isDevEnv() {
    return getApplicationEnvironment() == ENV.DEVELOPMENT;
  }
}
