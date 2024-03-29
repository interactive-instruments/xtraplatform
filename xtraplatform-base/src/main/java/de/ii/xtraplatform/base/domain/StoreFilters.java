/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableStoreFilters.Builder.class)
public interface StoreFilters {
  List<String> getEntityTypes();

  List<String> getEntityIds();

  @JsonIgnore
  @Value.Derived
  default String getAsLabel() {
    return String.format(
        "entityTypes=[%s], entityIds=[%s]",
        getEntityTypes().isEmpty() ? "*" : String.join(", ", getEntityTypes()),
        getEntityIds().isEmpty() ? "*" : String.join(", ", getEntityIds()));
  }
}
