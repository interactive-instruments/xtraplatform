/*
 * Copyright 2018-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.auth.app.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import de.ii.xtraplatform.auth.domain.ImmutableUser;
import de.ii.xtraplatform.auth.domain.Role;
import de.ii.xtraplatform.auth.domain.User;
import de.ii.xtraplatform.base.domain.AuthConfiguration;
import de.ii.xtraplatform.base.domain.AuthConfiguration.UserInfo;
import de.ii.xtraplatform.web.domain.HttpClient;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zahnen
 */
public class TokenAuthenticator implements Authenticator<String, User> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticator.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
  private static final TypeReference<Map<String, Object>> TYPE_REF =
      new TypeReference<Map<String, Object>>() {};

  private final AuthConfiguration authConfig;
  private final HttpClient httpClient;

  TokenAuthenticator(AuthConfiguration authConfig, HttpClient httpClient) {
    this.authConfig = authConfig;
    this.httpClient = httpClient;
  }

  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    if (authConfig.getUserInfo().isPresent()) {
      UserInfo userInfoProvider = authConfig.getUserInfo().get();
      try {
        String url =
            userInfoProvider.getEndpoint().replace("{{token}}", token).replace("{token}", token);
        InputStream response =
            httpClient.getAsInputStream(url, Map.of("Authorization", "Bearer " + token));

        Map<String, Object> userInfo = MAPPER.readValue(response, TYPE_REF);

        LOGGER.debug("USERINFO {}", userInfo);

        String name = (String) userInfo.get(userInfoProvider.getClaims().getUserName());
        Role role =
            Role.fromString(
                Optional.ofNullable(
                        (String)
                            userInfo.get(authConfig.getJwt().get().getClaims().getPermissions()))
                    .orElse("USER"));
        List<String> scopes =
            userInfo.get(userInfoProvider.getClaims().getPermissions()) instanceof String
                ? SPLITTER.splitToList(
                    (String) userInfo.get(userInfoProvider.getClaims().getPermissions()))
                : Optional.ofNullable(
                        (List<String>) userInfo.get(userInfoProvider.getClaims().getPermissions()))
                    .orElse(List.of());

        return Optional.of(ImmutableUser.builder().name(name).role(role).scopes(scopes).build());
      } catch (Throwable e) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("Error validating token", e);
        }
      }
    }

    return Optional.empty();
  }
}
