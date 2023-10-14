/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.values.domain.annotations;

import static java.lang.annotation.ElementType.TYPE;

import de.ii.xtraplatform.values.domain.ValueEncoding.FORMAT;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Value {

  String type();

  Class<?> builder();

  boolean cacheValues();

  FORMAT defaultFormat() default FORMAT.YAML;
}
