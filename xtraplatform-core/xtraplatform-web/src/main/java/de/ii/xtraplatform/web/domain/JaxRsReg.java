/*
 * Copyright 2017-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.domain;

import java.util.Set;

/** @author zahnen */
public interface JaxRsReg {
  void addService(Object service);

  Set<Object> getResources();

  void addChangeListener(JaxRsChangeListener changeListener);
}