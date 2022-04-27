/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/** @title BackgroundTasks */
public class BackgroundTasksConfiguration {

  /**
   * @en The maximum number of threads available for background processes. If requests are to be
   *     answered efficiently at all times, the value should not exceed half of the CPU cores.
   * @de Die maximale Anzahl an Threads, die für Hintergrundprozesse zur Verfügung stehen. Falls zu
   *     jeder Zeit Requests performant beantwortet können werden sollen, sollte der Wert die Hälfte
   *     der CPU-Kerne nicht überschreiten.
   * @default `1`
   */
  @Valid
  @NotNull
  @Min(1)
  @JsonProperty
  public int maxThreads = 1;
}
