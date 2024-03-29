/*
 * Copyright 2017-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.ii.xtraplatform.base.domain.LogContext;
import de.ii.xtraplatform.web.domain.MustacheRenderer;
import de.ii.xtraplatform.web.domain.MustacheResolverRegistry;
import io.dropwizard.views.common.View;
import io.dropwizard.views.common.ViewRenderException;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zahnen
 */
@Singleton
@AutoBind
public class FallbackMustacheViewRenderer extends MustacheViewRenderer implements MustacheRenderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FallbackMustacheViewRenderer.class);

  private final LoadingCache<Class<? extends View>, MustacheFactory> factories;
  private final MustacheResolverRegistry mustacheResolverRegistry;
  private boolean useCache = true;

  @Inject
  FallbackMustacheViewRenderer(MustacheResolverRegistry mustacheResolverRegistry) {
    this.mustacheResolverRegistry = mustacheResolverRegistry;
    this.factories =
        CacheBuilder.newBuilder()
            .build(
                new CacheLoader<Class<? extends View>, MustacheFactory>() {
                  @Override
                  public MustacheFactory load(Class<? extends View> key) throws Exception {
                    return createNewMustacheFactory(key);
                  }
                });
  }

  /*@Override
  public boolean isRenderable(View view) {
      return view.getTemplateName().endsWith(getSuffix());
  }*/

  @Override
  public void render(View view, Locale locale, OutputStream output) throws IOException {
    try {
      final Charset charset = view.getCharset().orElse(StandardCharsets.UTF_8);
      try (OutputStreamWriter writer = new OutputStreamWriter(output, charset)) {
        render(view, writer);
      }
    } catch (ViewRenderException e) {
      LogContext.error(LOGGER, e.getCause(), "Mustache error");
      throw e;
    }
  }

  @Override
  public void render(View view, OutputStreamWriter writer) throws IOException {
    try {
      final MustacheFactory mustacheFactory =
          useCache ? factories.get(view.getClass()) : createNewMustacheFactory(view.getClass());
      final Mustache template = mustacheFactory.compile(view.getTemplateName());

      template.execute(writer, view);
    } catch (Throwable e) {
      throw new ViewRenderException("Mustache template error: " + view.getTemplateName(), e);
    }
  }

  @Override
  public void configure(Map<String, String> options) {
    useCache = Optional.ofNullable(options.get("cache")).map(Boolean::parseBoolean).orElse(true);
  }

  @VisibleForTesting
  boolean isUseCache() {
    return useCache;
  }

  /*@Override
  public String getSuffix() {
      return ".mustache";
  }*/

  private MustacheFactory createNewMustacheFactory(Class<? extends View> key) {
    return new DefaultMustacheFactory(mustacheResolverRegistry.getResolverForClass(key));
  }
}
