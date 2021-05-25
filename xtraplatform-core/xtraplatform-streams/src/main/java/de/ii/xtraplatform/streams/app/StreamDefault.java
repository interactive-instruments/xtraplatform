package de.ii.xtraplatform.streams.app;

import de.ii.xtraplatform.streams.domain.Reactive.BasicStream;
import de.ii.xtraplatform.streams.domain.Reactive.RunnableStream;
import de.ii.xtraplatform.streams.domain.Reactive.Runner;
import de.ii.xtraplatform.streams.domain.Reactive.Sink;
import de.ii.xtraplatform.streams.domain.Reactive.Source;
import de.ii.xtraplatform.streams.domain.Reactive.Stream;
import de.ii.xtraplatform.streams.domain.Reactive.StreamWithResult;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Function;

public class StreamDefault<V, W> implements BasicStream<V, W>, StreamWithResult<V, W> {

  private final Source<V> source;
  private final Sink<V, W> sink;
  private final W result;
  private Optional<BiFunction<W, Throwable, W>> errorHandler;
  private Optional<BiFunction<W, V, W>> itemHandler;

  public StreamDefault(Source<V> source, Sink<V, W> sink) {
    this(source, sink, null);
  }

  StreamDefault(Source<V> source, Sink<V, W> sink, W initialResult) {
    this.source = source;
    this.sink = sink;
    this.result = initialResult;
    this.errorHandler = Optional.empty();
    this.itemHandler = Optional.empty();
  }

  @Override
  public RunnableStream<W> on(Runner runner) {
    return new RunnableStreamDefault<>(runner, this);
  }

  @Override
  public <W1> StreamWithResult<V, W1> withResult(W1 initial) {
    return new StreamDefault<>(source, ((SinkDefault<V, W>)sink).withResult(initial), initial);
  }

  @Override
  public StreamWithResult<V, W> handleError(BiFunction<W, Throwable, W> errorHandler) {
    this.errorHandler = Optional.of(errorHandler);

    return this;
  }

  @Override
  public StreamWithResult<V, W> handleItem(BiFunction<W, V, W> itemHandler) {
    this.itemHandler = Optional.of(itemHandler);

    return this;
  }

  @Override
  public <X> Stream<X> handleEnd(Function<W, X> finalizer) {
    return new WithFinalizer<>(finalizer);
  }

  public Source<V> getSource() {
    return source;
  }

  public Sink<V, W> getSink() {
    return sink;
  }

  public W getResult() {
    return result;
  }

  public Optional<BiFunction<W, Throwable, W>> getErrorHandler() {
    return errorHandler;
  }

  public Optional<BiFunction<W, V, W>> getItemHandler() {
    return itemHandler;
  }

  CompletionStage<W> onComplete(CompletionStage<W> resultStage) {
    CompletableFuture<W> completableFuture = new CompletableFuture<>();

    resultStage.whenComplete((result, throwable) -> {
      System.out.printf("onComplete %s%n", throwable);

      if (Objects.isNull(throwable)) {
        completableFuture.complete(result);
      } else {
        onError(throwable, completableFuture);
      }
    });

    return completableFuture;
  }

  void onError(Throwable throwable, CompletableFuture<W> resultFuture) {
    Throwable actualThrowable = throwable instanceof CompletionException && Objects
        .nonNull(throwable.getCause())
        ? throwable.getCause()
        : throwable;
    System.out.printf("onError %s%n", actualThrowable);

    if (errorHandler.isPresent()) {
      try {
        resultFuture.complete(errorHandler.get().apply(result, actualThrowable));
      } catch (Throwable handlerThrowable) {
        resultFuture.completeExceptionally(handlerThrowable);
      }
    } else {
      resultFuture.completeExceptionally(actualThrowable);
    }
  }

  class WithFinalizer<X> implements Stream<X> {
    private final Function<W, X> finalizer;

    WithFinalizer(Function<W, X> finalizer) {
      this.finalizer = finalizer;
    }

    @Override
    public RunnableStream<X> on(Runner runner) {
      return new RunnableStreamDefault<>(runner, this);
    }

    public StreamDefault<V, W> getStream() {
      return StreamDefault.this;
    }
    public Function<W, X> getFinalizer() {
      return finalizer;
    }
  }

  private static class RunnableStreamDefault<X> implements RunnableStream<X> {

    private final Runner runner;
    private final Stream<X> stream;

    RunnableStreamDefault(Runner runner, Stream<X> stream) {
      this.runner = runner;
      this.stream = stream;
    }

    @Override
    public CompletionStage<X> run() {
      return runner.run(stream);
    }
  }
}
