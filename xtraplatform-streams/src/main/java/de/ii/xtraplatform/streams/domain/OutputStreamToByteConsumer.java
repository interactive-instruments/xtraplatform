/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.streams.domain;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class OutputStreamToByteConsumer extends OutputStream {

  private Consumer<byte[]> byteConsumer;

  public OutputStreamToByteConsumer(Consumer<byte[]> byteConsumer) {
    super();
    this.byteConsumer = byteConsumer;
  }

  public OutputStreamToByteConsumer() {
    super();
  }

  public void setByteConsumer(Consumer<byte[]> byteConsumer) {
    this.byteConsumer = byteConsumer;
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    Objects.requireNonNull(byteConsumer, "OutputStream needs byteConsumer");
    Objects.checkFromIndexSize(off, len, b.length);

    byteConsumer.accept(Arrays.copyOfRange(b, off, len));
  }

  @Override
  public void write(int i) throws IOException {
    throw new UnsupportedOperationException();
  }
}
