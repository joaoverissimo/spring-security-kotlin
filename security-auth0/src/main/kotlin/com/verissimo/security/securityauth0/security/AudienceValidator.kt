package com.verissimo.security.securityauth0.security

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt

class AudienceValidator(
    private val audience: String
) : OAuth2TokenValidator<Jwt> {

  override fun validate(token: Jwt): OAuth2TokenValidatorResult {
    if (audience in token.audience) {
      return OAuth2TokenValidatorResult.success();
    }

    val error = OAuth2Error("invalid_token", "The required audience is missing", null)
    return OAuth2TokenValidatorResult.failure(error)
  }

}