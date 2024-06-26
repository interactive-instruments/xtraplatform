/*
 * Copyright 2018-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.entities.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ii.xtraplatform.docs.DocIgnore;
import de.ii.xtraplatform.values.domain.StoredValue;
import java.time.Instant;
import java.util.Optional;
import org.immutables.value.Value;

public interface EntityData extends StoredValue {

  /**
   * @langEn Unique identifier of the entity, has to match the filename. Allowed characters are
   *     (A-Z, a-z), numbers (0-9), underscore and hyphen.
   * @langDe Eindeutiger Identifikator des Entities, muss dem Dateinamen entsprechen. Erlaubt sind
   *     Buchstaben (A-Z, a-z), Ziffern (0-9), der Unterstrich ("_") und der Bindestrich ("-").
   */
  String getId();

  @DocIgnore
  @JsonIgnore
  @Value.Default
  @Value.Auxiliary
  default long getCreatedAt() {
    return Instant.now().toEpochMilli();
  }

  @DocIgnore
  @JsonIgnore
  @Value.Default
  @Value.Auxiliary
  default long getLastModified() {
    return Instant.now().toEpochMilli();
  }

  @DocIgnore
  @JsonIgnore
  @Value.Default
  default long getEntityStorageVersion() {
    return 1;
  }

  @DocIgnore
  @JsonIgnore
  @Value.Derived
  default long getEntitySchemaVersion() {
    return 1;
  }

  @DocIgnore
  @JsonIgnore
  Optional<String> getEntitySubType();

  /**
   * @langEn Option to disable the entity, which means it will not be available to other entities
   *     and background tasks will not be running.
   * @langDe Option um die Entity zu deaktivieren, was bedeutet, dass sie für andere Entities nicht
   *     verfügbar ist und Hintergrundprozesse nicht laufen.
   * @default true
   */
  @Value.Default
  default boolean getEnabled() {
    return true;
  }
}
