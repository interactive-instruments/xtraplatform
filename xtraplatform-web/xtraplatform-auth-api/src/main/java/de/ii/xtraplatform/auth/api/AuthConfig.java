/**
 * Copyright 2018 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.auth.api;

/**
 * @author zahnen
 */
public interface AuthConfig {

    String JWT_SIGNING_KEY = "jwtSigningKey";
    String USER_NAME_KEY = "userNameKey";
    String USER_ROLE_KEY = "userRoleKey";

    boolean isJwt();

    String getJwtSigningKey();

    default void setJwtSigningKey(String key) {}

    //String getUserInfoUrl();

    boolean isActive();

    //String getConnectionInfoEndpoint();

    String getUserNameKey();

    String getUserRoleKey();

    //String getExternalDynamicAuthorizationEndpoint();

    //String getPostProcessingEndpoint();
}