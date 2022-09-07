/*
 * Copyright 2019-2020 interactive instruments GmbH
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.ii.xtraplatform.auth.app;

import com.github.azahnen.dagger.annotations.AutoBind;
import com.google.common.base.Strings;
import de.ii.xtraplatform.auth.domain.ImmutableUser;
import de.ii.xtraplatform.auth.domain.Role;
import de.ii.xtraplatform.auth.domain.TokenHandler;
import de.ii.xtraplatform.auth.domain.User;
import de.ii.xtraplatform.base.domain.AppContext;
import de.ii.xtraplatform.base.domain.AuthConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@AutoBind
public class JwtTokenHandler implements TokenHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenHandler.class);

  private final AuthConfig authConfig;

  @Inject
  public JwtTokenHandler(AppContext appContext) {
    this.authConfig = appContext.getConfiguration().auth;
  }

  @Override
  public String generateToken(User user, int expiresIn, boolean rememberMe) {
    return generateToken(
        user, Date.from(Instant.now().plus(expiresIn, ChronoUnit.MINUTES)), rememberMe);
  }

  @Override
  public String generateToken(User user, Date expiration, boolean rememberMe) {
    JwtBuilder jwtBuilder =
        new DefaultJwtBuilder()
            .setSubject(user.getName())
            .claim(authConfig.userRoleKey, user.getRole().toString())
            .claim("rememberMe", rememberMe)
            .setExpiration(expiration);
    if (user.getForceChangePassword()) {
      jwtBuilder.claim("forceChangePassword", true);
    }
    return jwtBuilder.signWith(getKey()).compact();
  }

  @Override
  public Optional<User> parseToken(String token) {
    if (authConfig.isActive() && authConfig.isJwt()) {
      try {
        Claims claimsJws =
            new DefaultJwtParser()
                .setSigningKey(authConfig.jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();

        return Optional.of(
            ImmutableUser.builder()
                .name(claimsJws.getSubject())
                .role(
                    Role.fromString(
                        Optional.ofNullable(claimsJws.get(authConfig.userRoleKey, String.class))
                            .orElse("USER")))
                .build());
      } catch (Throwable e) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("Error validating token", e);
        }
      }
    }

    return Optional.empty();
  }

  @Override
  public <T> Optional<T> parseTokenClaim(String token, String name, Class<T> type) {
    if (authConfig.isActive() && authConfig.isJwt()) {
      try {
        Claims claimsJws =
            new DefaultJwtParser()
                .setSigningKey(authConfig.jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();

        return Optional.ofNullable(claimsJws.get(name, type));

      } catch (Throwable e) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("Error validating token", e);
        }
      }
    }

    return Optional.empty();
  }

  private Key getKey() {
    return Optional.ofNullable(Strings.emptyToNull(authConfig.jwtSigningKey))
        .map(Base64.getDecoder()::decode)
        .map(Keys::hmacShaKeyFor)
        .orElseGet(this::generateKey);
  }

  private SecretKey generateKey() {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    authConfig.jwtSigningKey = Base64.getEncoder().encodeToString(key.getEncoded());

    // TODO
    LOGGER.warn(
        "No valid 'jwtSigningKey' found in 'cfg.yml', using '{}'. If you do not set 'jwtSigningKey', it will change on every restart.",
        authConfig.jwtSigningKey);

    return key;
  }
}
