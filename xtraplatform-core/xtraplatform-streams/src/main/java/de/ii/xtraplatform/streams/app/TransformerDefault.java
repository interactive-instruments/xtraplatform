package de.ii.xtraplatform.streams.app;

import de.ii.xtraplatform.streams.domain.Reactive.Transformer;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransformerDefault<T, U> implements Transformer<T, U> {

  public enum Type {MAP, PEEK}

  private final Type type;
  private final Function<T, U> function;
  private final Consumer<T> consumer;

  public TransformerDefault(Function<T, U> function) {
    this(Type.MAP, function, null);
  }

  public TransformerDefault(Consumer<T> consumer) {
    this(Type.PEEK, null, consumer);
  }

  TransformerDefault(Type type, Function<T, U> function, Consumer<T> consumer) {
    this.type = type;
    this.function = function;
    this.consumer = consumer;
  }

  public Type getType() {
    return type;
  }

  public Function<T, U> getFunction() {
    return function;
  }

  public Consumer<T> getConsumer() {
    return consumer;
  }
}
