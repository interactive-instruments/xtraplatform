/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xsf.core.rest;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author zahnen
 */
public interface InjectableContext<T> {
    void inject(ContainerRequestContext requestContext, T injectable);
}
