/*
 * Copyright 2015-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.services.domain;

import java.util.Optional;

/**
 * @author zahnen
 */
public interface ServiceBackgroundTasks {

  String COMMON_QUEUE = "COMMON_QUEUE";

  TaskQueue createQueue(String taskType);

  Optional<TaskStatus> getCurrentTask();

  Optional<TaskStatus> getCurrentTaskForService(String id);
}
