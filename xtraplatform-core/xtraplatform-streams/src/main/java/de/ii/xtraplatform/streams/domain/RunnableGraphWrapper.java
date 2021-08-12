/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.streams.domain;

import akka.stream.javadsl.RunnableGraph;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface RunnableGraphWrapper<T> {

  RunnableGraph<CompletionStage<T>> getGraph();

  default Function<Throwable, T> getExceptionHandler() {
    return throwable -> null;
  }
}
