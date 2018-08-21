/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.akka.http;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import org.osgi.framework.BundleContext;

/**
 * @author zahnen
 */
public interface ActorSystemProvider {
    ActorSystem getActorSystem(BundleContext context, Config config);
}
