/*
 * Copyright 2022 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.store.domain;

import com.github.azahnen.dagger.annotations.AutoMultiBind;
import de.ii.xtraplatform.base.domain.StoreSource;
import de.ii.xtraplatform.base.domain.StoreSource.Type;
import java.io.IOException;

@AutoMultiBind
public interface BlobStoreDriver {

  Type getType();

  boolean isAvailable(StoreSource storeSource);

  BlobSource init(StoreSource storeSource) throws IOException;
}