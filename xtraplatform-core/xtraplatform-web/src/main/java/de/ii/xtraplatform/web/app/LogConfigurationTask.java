/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.app;

import ch.qos.logback.classic.LoggerContext;
import com.github.azahnen.dagger.annotations.AutoBind;
import com.google.common.base.Splitter;
import de.ii.xtraplatform.web.domain.Dropwizard;
import de.ii.xtraplatform.runtime.domain.ThirdPartyLoggingFilter;
import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AutoBind
public class LogConfigurationTask extends Task {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogConfigurationTask.class);
  private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

  private final LoggerContext loggerContext;

  // TODO: DropwizardEnvironmentPlugin, AdminTaskRegistry
  @Inject
  protected LogConfigurationTask(Dropwizard dropwizard) {
    super("log-filter");
    this.loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    dropwizard.getEnvironment().admin().addTask(this);
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {
    if (LOGGER.isTraceEnabled()) LOGGER.trace("Log filter request: {}", parameters);

    Optional<ThirdPartyLoggingFilter> optionalThirdPartyLoggingFilter =
        loggerContext.getTurboFilterList().stream()
            .filter(turboFilter -> turboFilter instanceof ThirdPartyLoggingFilter)
            .map(turboFilter -> (ThirdPartyLoggingFilter) turboFilter)
            .findFirst();

    optionalThirdPartyLoggingFilter.ifPresent(
        thirdPartyLoggingFilter -> {
          getFiltersToEnable(parameters)
              .forEach(filter -> setFilter(thirdPartyLoggingFilter, filter, true));
          getFiltersToDisable(parameters)
              .forEach(filter -> setFilter(thirdPartyLoggingFilter, filter, false));
        });
  }

  private void setFilter(
      ThirdPartyLoggingFilter thirdPartyLoggingFilter, String filter, boolean enable) {
    switch (filter) {
      case "sqlQueries":
        thirdPartyLoggingFilter.setSqlQueries(enable);
        break;
      case "sqlResults":
        thirdPartyLoggingFilter.setSqlResults(enable);
        break;
      case "configDumps":
        thirdPartyLoggingFilter.setConfigDumps(enable);
        break;
      case "stackTraces":
        thirdPartyLoggingFilter.setStackTraces(enable);
        break;
      case "*":
        thirdPartyLoggingFilter.setSqlQueries(enable);
        thirdPartyLoggingFilter.setSqlResults(enable);
        thirdPartyLoggingFilter.setConfigDumps(enable);
        thirdPartyLoggingFilter.setStackTraces(enable);
        break;
    }
  }

  private List<String> getFiltersToEnable(Map<String, List<String>> parameters) {
    return getValueList(parameters.get("enable"));
  }

  private List<String> getFiltersToDisable(Map<String, List<String>> parameters) {
    return getValueList(parameters.get("disable"));
  }

  private List<String> getValueList(Collection<String> values) {
    return values.stream()
        .flatMap(
            value -> {
              if (value.contains(",")) {
                return SPLITTER.splitToList(value).stream();
              }
              return Stream.of(value);
            })
        .collect(Collectors.toList());
  }
}
