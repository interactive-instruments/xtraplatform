/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.dashboard.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import de.ii.xtraplatform.base.domain.AppContext;
import de.ii.xtraplatform.web.domain.StaticResources;
import javax.inject.Inject;
import javax.inject.Singleton;

// TODO: use AdminSubEndpoint instead, or replace html template in AdminEndpoint
@Singleton
@AutoBind
public class StaticResourcesDashboard implements StaticResources {

  private final boolean isEnabled;

  @Inject
  StaticResourcesDashboard(AppContext appContext) {
    this.isEnabled = true;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  @Override
  public String getResourcePath() {
    return "/dashboard";
  }

  @Override
  public String getUrlPath() {
    return "/dashboard";
  }
}
