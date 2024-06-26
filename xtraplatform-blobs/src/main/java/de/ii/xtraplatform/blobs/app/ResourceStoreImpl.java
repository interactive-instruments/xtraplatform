/*
 * Copyright 2023 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.blobs.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import dagger.Lazy;
import de.ii.xtraplatform.base.domain.AppLifeCycle;
import de.ii.xtraplatform.base.domain.Store;
import de.ii.xtraplatform.base.domain.StoreSource.Content;
import de.ii.xtraplatform.base.domain.resiliency.VolatileRegistry;
import de.ii.xtraplatform.blobs.domain.BlobStoreDriver;
import de.ii.xtraplatform.blobs.domain.ResourceStore;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@AutoBind
public class ResourceStoreImpl extends BlobStoreImpl implements ResourceStore, AppLifeCycle {

  private final CompletableFuture<Void> ready;

  @Inject
  public ResourceStoreImpl(
      Store store, VolatileRegistry volatileRegistry, Lazy<Set<BlobStoreDriver>> drivers) {
    super(store, volatileRegistry, drivers, Content.RESOURCES);
    this.ready = new CompletableFuture<>();
  }

  @Override
  public int getPriority() {
    return 20;
  }

  @Override
  public CompletionStage<Void> onStart(boolean isStartupAsync) {
    super.start();
    ready.complete(null);

    return CompletableFuture.completedFuture(null);
  }

  @Override
  public CompletableFuture<Void> onReady() {
    return ready;
  }

  @Override
  public Path getPrefix() {
    return Path.of("");
  }
}
