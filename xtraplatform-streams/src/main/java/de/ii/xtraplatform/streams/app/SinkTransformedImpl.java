/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.streams.app;

import de.ii.xtraplatform.streams.domain.Reactive.SinkReduced;
import de.ii.xtraplatform.streams.domain.Reactive.SinkReducedTransformed;
import de.ii.xtraplatform.streams.domain.Reactive.Transformer;
import java.util.concurrent.atomic.AtomicReference;

public class SinkTransformedImpl<T, U, V> implements SinkReducedTransformed<T, U, V> {

  private final Transformer<T, U> transformer;
  private final SinkReduced<U, V> sink;

  public SinkTransformedImpl(Transformer<T, U> transformer, SinkReduced<U, V> sink) {
    this.transformer = transformer;
    this.sink = sink;
  }

  public Transformer<T, U> getTransformer() {
    return transformer;
  }

  public SinkReduced<U, V> getSink() {
    return sink;
  }

  public AtomicReference<V> getItem() {
    if (sink instanceof SinkDefault) {
      return ((SinkDefault<U, V>) sink).getItem();
    }
    if (sink instanceof SinkTransformedImpl) {
      return ((SinkTransformedImpl<?, ?, V>) sink).getItem();
    }
    return new AtomicReference<>();
  }

  <V1> SinkReducedTransformed<T, U, V1> withResult(V1 initial) {
    if (sink instanceof SinkTransformedImpl) {
      return new SinkTransformedImpl<>(
          transformer, ((SinkTransformedImpl<U, ?, V>) sink).withResult(initial));
    }
    return new SinkTransformedImpl<>(transformer, ((SinkDefault<U, V>) sink).withResult(initial));
  }
}
