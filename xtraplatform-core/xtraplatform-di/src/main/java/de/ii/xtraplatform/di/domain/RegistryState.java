/*
 * Copyright 2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.di.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.felix.ipojo.architecture.PropertyDescription;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegistryState<T> implements Registry.State<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegistryState.class);
  private static final Joiner JOINER = Joiner.on('.').skipNulls();

  private final String componentName;
  private final BundleContext bundleContext;
  private final ImmutableList<String> componentProperties;
  private final Map<String, T> items;

  public RegistryState(String name, BundleContext bundleContext, String... componentProperties) {
    this.componentName = name;
    this.bundleContext = bundleContext;
    this.componentProperties = ImmutableList.copyOf(componentProperties);
    this.items = new ConcurrentHashMap<>();
  }

  @Override
  public Optional<T> get(String... componentPropertyValues) {
    return Optional.ofNullable(items.get(JOINER.join(componentPropertyValues)));
  }

  @Override
  public synchronized Optional<T> onArrival(ServiceReference<T> ref) {
    if (Objects.nonNull(ref)) {
      Optional<String> identifier = getComponentIdentifier(ref, componentProperties);
      T service = bundleContext.getService(ref);

      if (identifier.isPresent()) {
        this.items.put(identifier.get(), service);

        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Registered {}: {}", componentName, identifier.get());
        }
      }

      return Optional.ofNullable(service);
    }

    return Optional.empty();
  }

  @Override
  public synchronized Optional<T> onDeparture(ServiceReference<T> ref) {
    if (Objects.nonNull(ref)) {
      Optional<String> identifier = getComponentIdentifier(ref, componentProperties);

      if (identifier.isPresent()) {
        this.items.remove(identifier.get());

        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Deregistered {}: {}", componentName, identifier.get());
        }
      }
    }

    return Optional.empty();
  }

  private Optional<String> getComponentIdentifier(
      ServiceReference<T> component, List<String> properties) {
    final String identifier =
        properties.stream()
            .map(property -> getComponentProperty(component, property))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.joining("."));

    return Optional.ofNullable(Strings.emptyToNull(identifier));
  }

  private Optional<String> getComponentProperty(ServiceReference<T> component, String property) {
    if (Objects.isNull(component) || Objects.isNull(Strings.emptyToNull(property))) {
      return Optional.empty();
    }

    return Arrays.stream((PropertyDescription[]) component.getProperty("component.properties"))
        .filter(pd -> Objects.equals(pd.getName(), property))
        .map(PropertyDescription::getValue)
        .findFirst();
  }
}