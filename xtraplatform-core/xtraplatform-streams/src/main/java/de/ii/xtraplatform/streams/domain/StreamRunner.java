/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.streams.domain;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.japi.function.Function2;
import akka.japi.function.Procedure;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerSettings;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.JavaFlowSupport;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.ii.xtraplatform.runtime.domain.LogContext;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutor;

public class StreamRunner implements Closeable {

  private static final Logger LOGGER = LoggerFactory.getLogger(StreamRunner.class);
  public static final int DYNAMIC_CAPACITY = -1;

  private final ActorMaterializer materializer;
  private final String name;
  private final int capacity;
  private final int queueSize;
  private final ConcurrentLinkedQueue<Runnable> queue;
  private final AtomicInteger running;

  public StreamRunner(BundleContext context, ActorSystemProvider actorSystemProvider, String name) {
    this(context, actorSystemProvider, name, DYNAMIC_CAPACITY, DYNAMIC_CAPACITY);
  }

  public StreamRunner(
      BundleContext context,
      ActorSystemProvider actorSystemProvider,
      String name,
      int capacity,
      int queueSize) {
    this(actorSystemProvider.getActorSystem(context, getConfig(name, capacity), "akka"), name,
        capacity, queueSize);
  }

  StreamRunner(
      ActorSystem actorSystem,
      String name,
      int capacity,
      int queueSize) {
    if (capacity != 0) {
      ActorMaterializerSettings settings =
          ActorMaterializerSettings.create(actorSystem).withDispatcher(getDispatcherName(name));

      this.materializer = ActorMaterializer.create(settings, actorSystem);
    } else {
      this.materializer = null;
    }
    this.name = name;
    this.capacity = capacity;
    this.queueSize = queueSize;
    this.queue = new ConcurrentLinkedQueue<>();
    this.running = new AtomicInteger(0);
  }

  //2x
  public <T, U, V> CompletionStage<V> run(Source<T, U> source, Sink<T, CompletionStage<V>> sink) {
    return run(LogContextStream.graphWithMdc(source, sink, Keep.right()));
  }

  //5x
  public <U> CompletionStage<U> run(RunnableGraphWrapper<U> graph) {
    return runGraph(null);
  }

  public <T, U, V, W> CompletionStage<W> run(ReactiveStream<T, U, V, W> reactiveStream) {
    try {
      Source<T, NotUsed> source = JavaFlowSupport.Source
          .fromPublisher(reactiveStream.getSource().with(materializer));

      Flow<T, U, NotUsed> flow = JavaFlowSupport.Flow
          .fromProcessor(() -> reactiveStream.getProcessor().with(materializer));

      Flow<U, U, NotUsed> sink = JavaFlowSupport.Flow
          .fromProcessor(() -> reactiveStream.getSink().with(materializer));

      Source<U, NotUsed> resultSource = LogContextStream.withMdc(source)
          .via(flow)
          .via(sink);

      RunnableGraph<CompletionStage<W>> graph;

      if (reactiveStream.getResultCombiner().isPresent()) {
        Sink<U, CompletionStage<V>> combinerSink = Sink
            .fold(reactiveStream.getEmptyResult(),
                (result, item) -> reactiveStream.getResultCombiner().get().apply(result, item));

        graph = resultSource.toMat(combinerSink, (left, right) -> reactiveStream.onComplete(right));
      } else {
        graph = resultSource.toMat(Sink.ignore(), (left, right) -> reactiveStream
            .onComplete(right.thenApply(done -> reactiveStream.getEmptyResult())));
      }

      return runGraph(graph);

    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private <U> CompletionStage<U> runGraph(RunnableGraph<CompletionStage<U>> graph) {

    CompletableFuture<U> completableFuture = new CompletableFuture<>();

    if (getCapacity() == DYNAMIC_CAPACITY) {
      graph.run(materializer)
          .exceptionally(
              throwable -> {
                completableFuture.completeExceptionally(throwable);
                return null;
              })
          .thenAccept(completableFuture::complete);
    } else {
      Runnable task =
          () -> {
            graph.run(materializer)
                .exceptionally(
                    throwable -> {
                      completableFuture.completeExceptionally(throwable);

                      runNext();

                      return null;
                    })
                .thenAccept(
                    LogContext.withMdc(
                        t -> {
                          completableFuture.complete(t);

                          runNext();
                        }));
          };
      run(task);
    }

    return completableFuture;
  }

  private void run(Runnable task) {
    synchronized (running) {
      if (running.get() < queueSize) {
        running.incrementAndGet();
        task.run();
      } else {
        queue.offer(task);
      }
    }
  }

  private void runNext() {
    synchronized (running) {
      int current = running.get() - 1;
      if (current < queueSize) {
        Runnable task = queue.poll();
        if (Objects.nonNull(task)) {
          task.run();
        } else {
          running.decrementAndGet();
        }
      }
    }
  }

  public ExecutionContextExecutor getDispatcher() {
    return materializer.system().dispatcher();
  }

  public int getCapacity() {
    return capacity;
  }

  private static Config getConfig(String name, int capacity) {
    return capacity == DYNAMIC_CAPACITY ? getDefaultConfig(name)
        : getConfig(name, capacity, capacity);
  }

  private static Config getDefaultConfig(String name) {
    return getConfig(name, 8, 64);
  }

  private static Config getConfig(String name, int parallelismMin, int parallelismMax) {
    return ConfigFactory.parseMap(
        new ImmutableMap.Builder<String, Object>()
            .put("akka.stdout-loglevel", "OFF")
            .put("akka.loglevel", "INFO")
            .put("akka.loggers", ImmutableList.of("akka.event.slf4j.Slf4jLogger"))
            .put("akka.logging-filter", "akka.event.slf4j.Slf4jLoggingFilter")
            // .put("akka.log-config-on-start", true)
            .put(String.format("%s.type", getDispatcherName(name)), "Dispatcher")
            // .put(String.format("%s.executor", getDispatcherName(name)), "fork-join-executor")
            .put(
                String.format("%s.executor", getDispatcherName(name)),
                "de.ii.xtraplatform.streams.app.StreamExecutorServiceConfigurator")
            .put(
                String.format("%s.fork-join-executor.parallelism-min", getDispatcherName(name)),
                parallelismMin)
            .put(
                String.format("%s.fork-join-executor.parallelism-factor", getDispatcherName(name)),
                1.0)
            .put(
                String.format("%s.fork-join-executor.parallelism-max", getDispatcherName(name)),
                parallelismMax)
            .put(
                String.format("%s.fork-join-executor.task-peeking-mode", getDispatcherName(name)),
                "FIFO")
            .build());
  }

  private static String getDispatcherName(String name) {
    return String.format("stream.%s", name);
  }

  @Override
  public void close() throws IOException {
    if (Objects.nonNull(materializer)) {
      materializer.shutdown();
    }
  }
}
