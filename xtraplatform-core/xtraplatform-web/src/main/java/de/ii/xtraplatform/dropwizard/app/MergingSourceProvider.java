/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.dropwizard.app;

import static java.util.Objects.requireNonNull;

import com.google.common.io.ByteSource;
import de.ii.xtraplatform.runtime.domain.ConfigurationReader;
import de.ii.xtraplatform.runtime.domain.Constants;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MergingSourceProvider implements ConfigurationSourceProvider {

  private final ConfigurationSourceProvider delegate;
  private final ConfigurationReader configurationReader;
  private final Constants.ENV env;

  /**
   * Create a new instance.
   *
   * @param delegate The underlying {@link ConfigurationSourceProvider}.
   * @param mergeAfterBase
   * @param env
   */
  public MergingSourceProvider(
      ConfigurationSourceProvider delegate, List<ByteSource> mergeAfterBase, Constants.ENV env) {
    this.delegate = requireNonNull(delegate);
    this.configurationReader = new ConfigurationReader(mergeAfterBase);
    this.env = env;
  }

  /** {@inheritDoc} */
  @Override
  public InputStream open(String path) throws IOException {
    try (final InputStream in = delegate.open(path)) {
      return configurationReader.loadMergedConfig(in, env);
    }
  }
}
