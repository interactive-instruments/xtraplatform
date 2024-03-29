/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.domain;

import com.github.azahnen.dagger.annotations.AutoMultiBind;
import java.util.Optional;

@AutoMultiBind
public interface StaticResources {

  String getResourcePath();

  String getUrlPath();

  default boolean isEnabled() {
    return true;
  }

  default Optional<StaticResourceReader> getResourceReader() {
    return Optional.empty();
  }
}
