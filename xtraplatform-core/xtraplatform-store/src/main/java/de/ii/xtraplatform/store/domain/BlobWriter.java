/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.store.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface BlobWriter {

  void put(Path path, InputStream content) throws IOException;

  void delete(Path path) throws IOException;
}
