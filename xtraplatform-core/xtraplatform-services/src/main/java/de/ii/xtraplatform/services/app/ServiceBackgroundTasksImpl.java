/*
 * Copyright 2021 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.services.app;

import de.ii.xtraplatform.di.domain.Registry;
import de.ii.xtraplatform.di.domain.RegistryState;
import de.ii.xtraplatform.dropwizard.domain.XtraPlatform;
import de.ii.xtraplatform.services.domain.Scheduler;
import de.ii.xtraplatform.services.domain.Service;
import de.ii.xtraplatform.services.domain.ServiceBackgroundTask;
import de.ii.xtraplatform.services.domain.ServiceBackgroundTasks;
import de.ii.xtraplatform.services.domain.Task;
import de.ii.xtraplatform.services.domain.TaskQueue;
import de.ii.xtraplatform.services.domain.TaskStatus;
import de.ii.xtraplatform.store.domain.entities.EntityRegistry;
import de.ii.xtraplatform.store.domain.entities.Reloadable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.whiteboard.Wbp;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Provides
@Instantiate
@Wbp(
    filter =
        Registry.FILTER_PREFIX
            + ServiceBackgroundTasksImpl.SERVICE_BACKGROUND_TASK
            + Registry.FILTER_SUFFIX,
    onArrival = Registry.ON_ARRIVAL_METHOD,
    onDeparture = Registry.ON_DEPARTURE_METHOD)
public class ServiceBackgroundTasksImpl
    implements ServiceBackgroundTasks, Registry<ServiceBackgroundTask<? extends Service>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBackgroundTasksImpl.class);
  static final String SERVICE_BACKGROUND_TASK =
      "de.ii.xtraplatform.services.domain.ServiceBackgroundTask";

  private final Registry.State<ServiceBackgroundTask<? extends Service>> tasks;
  private final Scheduler scheduler;
  private final TaskQueue commonQueue;
  private final Map<String, TaskQueue> taskQueues;

  ServiceBackgroundTasksImpl(
      @Context BundleContext context,
      @Requires XtraPlatform xtraPlatform,
      @Requires Scheduler scheduler,
      @Requires EntityRegistry entityRegistry) {
    this.tasks = new RegistryState<>(SERVICE_BACKGROUND_TASK, context);
    this.scheduler = scheduler;
    this.commonQueue = scheduler.createQueue(ServiceBackgroundTasks.COMMON_QUEUE, xtraPlatform.getConfiguration().backgroundTasks.maxThreads);
    this.taskQueues = new ConcurrentHashMap<>();
    taskQueues.put(COMMON_QUEUE, commonQueue);
    entityRegistry.addEntityListener(Service.class, this::onServiceStart, true);
    entityRegistry.addEntityGoneListener(Service.class, this::onServiceStop);
  }

  private <T extends Service> void onServiceStart(T service) {
    if (service instanceof Reloadable) {
      ((Reloadable) service).addReloadListener(service.getClass(), this::onServiceReload);
    }
    startTasks(service);
  }

  private <T extends Service> void onServiceReload(T service) {
    stopTasks(service);
    startTasks(service);
  }

  private <T extends Service> void onServiceStop(T service) {
    stopTasks(service);
  }

  private <T extends Service> void startTasks(T service) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Starting background tasks for service '{}'", service.getId());
    }
    tasks
        .get()
        .forEach(
            task -> {
              scheduleIfMatching(service, task);
            });
  }

  private <T extends Service> void stopTasks(T service) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Stopping background tasks for service '{}'", service.getId());
    }
    getCurrentTaskForService(service.getId()).ifPresent(TaskStatus::stop);
    commonQueue.getFutureTasks().stream()
        .filter(task -> Objects.equals(task.getId(), service.getId()))
        .forEach(commonQueue::remove);
  }

  private <T extends Service, U extends Service> void scheduleIfMatching(
      T service, ServiceBackgroundTask<U> task) {
    Class<U> serviceType = task.getServiceType();
    if (serviceType.isAssignableFrom(service.getClass())) {
      schedule(serviceType.cast(service), task);
    }
  }

  private <T extends Service> void schedule(T service, ServiceBackgroundTask<T> task) {
    if (task.runOnStart(service)) {
      taskQueues.get(task.getQueue()).launch(task.getTask(service, task.getLabel()), 5000);
    }
    if (task.runPeriodic(service).isPresent()) {
      TaskQueue taskQueue = taskQueues.get(task.getQueue());
      Task task1 = task.getTask(service, task.getLabel());
      scheduler.schedule(() -> taskQueue.launch(task1), task.runPeriodic(service).get());
    }
  }

  @Override
  public TaskQueue createQueue(String taskType) {
    TaskQueue queue = scheduler.createQueue(taskType, 1);
    taskQueues.put(taskType, queue);

    return queue;
  }

  @Override
  public Optional<TaskStatus> getCurrentTask() {
    return commonQueue.getCurrentTask();
  }

  @Override
  public Optional<TaskStatus> getCurrentTaskForService(String id) {
    return getCurrentTask().filter(taskStatus -> Objects.equals(taskStatus.getId(), id));
  }

  @Override
  public State<ServiceBackgroundTask<?>> getRegistryState() {
    return tasks;
  }
}
