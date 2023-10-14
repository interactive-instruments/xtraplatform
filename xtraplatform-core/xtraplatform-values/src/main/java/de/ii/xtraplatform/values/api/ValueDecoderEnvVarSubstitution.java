/*
 * Copyright 2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.values.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ii.xtraplatform.values.domain.Identifier;
import de.ii.xtraplatform.values.domain.ValueDecoderMiddleware;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.text.StringSubstitutor;

public class ValueDecoderEnvVarSubstitution implements ValueDecoderMiddleware<byte[]> {

  private final StringSubstitutor substitutor;

  public ValueDecoderEnvVarSubstitution() {
    this.substitutor = new EnvironmentVariableSubstitutor(false, true);
  }

  @Override
  public byte[] process(
      Identifier identifier,
      byte[] payload,
      ObjectMapper objectMapper,
      byte[] data,
      boolean ignoreCache)
      throws IOException {

    final String config = new String(payload, StandardCharsets.UTF_8);
    final String substituted = substitutor.replace(config);

    return substituted.getBytes(StandardCharsets.UTF_8);
  }
}
