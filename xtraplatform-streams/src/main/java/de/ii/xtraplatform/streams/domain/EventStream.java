/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.streams.domain;

import de.ii.xtraplatform.streams.domain.Reactive.Sink;
import de.ii.xtraplatform.streams.domain.Reactive.Source;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class EventStream<T extends Event> {
  private final Reactive.Runner streamRunner;
  private final String eventType;
  private final Queue<T> eventQueue;
  private final Flowable<T> eventStream;
  private FlowableEmitter<T> emitter;

  public EventStream(Reactive.Runner streamRunner, String eventType) {
    this.streamRunner = streamRunner;
    this.eventType = eventType;
    this.eventQueue = new ConcurrentLinkedQueue<>();
    this.eventStream =
        Flowable.create(
            emitter -> {
              while (!eventQueue.isEmpty()) {
                emitter.onNext(eventQueue.remove());
              }
              this.emitter = emitter;
            },
            BackpressureStrategy.BUFFER);
  }

  public void foreach(Consumer<T> eventConsumer) {
    Source.publisher(eventStream).to(Sink.foreach(eventConsumer)).on(streamRunner).run();
  }

  public void queue(T event) {
    synchronized (this) {
      if (Objects.nonNull(emitter)) {
        emitter.onNext(event);
      }
      eventQueue.offer(event);
    }
  }

  public String getEventType() {
    return eventType;
  }
}
