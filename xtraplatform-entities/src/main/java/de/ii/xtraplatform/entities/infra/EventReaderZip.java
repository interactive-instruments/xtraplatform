/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.entities.infra;

import de.ii.xtraplatform.base.domain.StoreDriver;
import de.ii.xtraplatform.base.domain.util.Tuple;
import de.ii.xtraplatform.base.domain.util.ZipWalker;
import de.ii.xtraplatform.entities.domain.EventReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventReaderZip implements EventReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventReaderZip.class);

  @Override
  public Stream<Tuple<Path, Supplier<byte[]>>> load(
      Path sourcePath, List<String> includes, List<String> excludes) throws IOException {
    // TODO: archiveRoot
    List<PathMatcher> includeMatchers = StoreDriver.asMatchers(includes, "**");
    List<PathMatcher> excludeMatchers = StoreDriver.asMatchers(excludes, "**");
    List<Tuple<Path, Supplier<byte[]>>> entries = new ArrayList<>();

    ZipWalker.walkEntries(
        sourcePath,
        (zipEntry, payload) -> {
          if ((!includeMatchers.isEmpty()
                  && includeMatchers.stream().noneMatch(exclude -> exclude.matches(zipEntry)))
              || excludeMatchers.stream().anyMatch(exclude -> exclude.matches(zipEntry))) {
            return;
          }

          // have to read here because zip is closed after return of entries
          byte[] bytes = payload.get();
          entries.add(Tuple.of(zipEntry, () -> bytes));
        });

    return entries.stream();
  }
}
