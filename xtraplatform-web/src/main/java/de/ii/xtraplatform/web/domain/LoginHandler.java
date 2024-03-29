/*
 * Copyright 2023 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.web.domain;

import com.github.azahnen.dagger.annotations.AutoMultiBind;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@AutoMultiBind
public interface LoginHandler {

  String PATH_LOGIN = "/_login";
  String PATH_CALLBACK = "/_callback";
  String PATH_LOGOUT = "/_logout";
  String PARAM_LOGIN_REDIRECT_URI = "redirect_uri";
  String PARAM_LOGIN_SCOPES = "scopes";
  String PARAM_CALLBACK_STATE = "state";
  String PARAM_CALLBACK_TOKEN = "access_token";
  String PARAM_LOGOUT_REDIRECT_URI = "post_logout_redirect_uri";
  String PARAM_LOGOUT_CLIENT_ID = "client_id";

  Response handle(
      ContainerRequestContext containerRequestContext,
      String redirectUri,
      String scopes,
      String rootPath,
      boolean isCallback,
      String state,
      String token);

  Response logout(ContainerRequestContext containerRequestContext, String redirectUri);
}
