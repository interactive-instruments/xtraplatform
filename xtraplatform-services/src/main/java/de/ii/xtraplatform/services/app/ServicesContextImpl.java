/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.services.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import de.ii.xtraplatform.base.domain.AppContext;
import de.ii.xtraplatform.services.domain.ServicesContext;
import java.net.URI;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@AutoBind
public class ServicesContextImpl implements ServicesContext {

  private final URI uri;

  @Inject
  ServicesContextImpl(AppContext appContext) {
    this.uri = appContext.getUri();
  }

  @Override
  public URI getUri() {
    return uri;
  }
}
