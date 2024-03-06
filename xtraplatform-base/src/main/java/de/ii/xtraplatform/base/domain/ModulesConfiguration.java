/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.ii.xtraplatform.docs.DocFile;
import de.ii.xtraplatform.docs.DocStep;
import de.ii.xtraplatform.docs.DocStep.Step;
import de.ii.xtraplatform.docs.DocTable;
import de.ii.xtraplatform.docs.DocTable.ColumnSet;
import org.immutables.value.Value;

/**
 * @langAll Modules
 *     <p>## Options
 *     <p>{@docTable:properties}
 */
@DocFile(
    path = "application/20-configuration",
    name = "80-modules.md",
    tables = {
      @DocTable(
          name = "properties",
          rows = {@DocStep(type = Step.JSON_PROPERTIES)},
          columnSet = ColumnSet.JSON_PROPERTIES)
    })
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(as = ModifiableModulesConfiguration.class)
public interface ModulesConfiguration {

  enum Startup {
    ASYNC,
    SYNC
  }

  enum Maturity {
    PROPOSAL,
    CANDIDATE,
    MATURE
  }

  enum Maintenance {
    NONE,
    LOW,
    FULL
  }

  /**
   * @en The maximum number of threads available for background processes. If requests are to be
   *     answered efficiently at all times, the value should not exceed half of the CPU cores.
   * @de Die maximale Anzahl an Threads, die für Hintergrundprozesse zur Verfügung stehen. Falls zu
   *     jeder Zeit Requests performant beantwortet können werden sollen, sollte der Wert die Hälfte
   *     der CPU-Kerne nicht überschreiten.
   * @default ASYNC
   */
  @Value.Default
  default Startup getStartup() {
    return Startup.ASYNC;
  }

  @JsonIgnore
  @Value.Derived
  default boolean isStartupAsync() {
    return getStartup() == Startup.ASYNC;
  }

  /**
   * @en The maximum number of threads available for background processes. If requests are to be
   *     answered efficiently at all times, the value should not exceed half of the CPU cores.
   * @de Die maximale Anzahl an Threads, die für Hintergrundprozesse zur Verfügung stehen. Falls zu
   *     jeder Zeit Requests performant beantwortet können werden sollen, sollte der Wert die Hälfte
   *     der CPU-Kerne nicht überschreiten.
   * @default ASYNC
   */
  @Value.Default
  default Maturity getMinMaturity() {
    return Maturity.CANDIDATE;
  }

  /**
   * @en The maximum number of threads available for background processes. If requests are to be
   *     answered efficiently at all times, the value should not exceed half of the CPU cores.
   * @de Die maximale Anzahl an Threads, die für Hintergrundprozesse zur Verfügung stehen. Falls zu
   *     jeder Zeit Requests performant beantwortet können werden sollen, sollte der Wert die Hälfte
   *     der CPU-Kerne nicht überschreiten.
   * @default ASYNC
   */
  @Value.Default
  default Maintenance getMinMaintenance() {
    return Maintenance.LOW;
  }
}
