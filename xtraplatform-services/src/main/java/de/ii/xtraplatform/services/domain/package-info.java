/**
 * Copyright 2020 interactive instruments GmbH
 *
 * <p>This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy
 * of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@Value.Style(deepImmutablesDetection = true, attributeBuilderDetection = true, builder = "new")
@AutoModule(
    single = true,
    encapsulate = true,
    multiBindings = {ContainerRequestFilter.class, ContainerResponseFilter.class, Binder.class})
package de.ii.xtraplatform.services.domain;

import com.github.azahnen.dagger.annotations.AutoModule;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import org.glassfish.jersey.internal.inject.Binder;
import org.immutables.value.Value;
