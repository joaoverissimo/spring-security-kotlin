package com.verissimo.security.securityauth0.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Value("\${auth0.audience}")
  private lateinit var audience: String

  @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private lateinit var issuer: String

  override fun configure(http: HttpSecurity) {
    http.authorizeRequests()
        .mvcMatchers("/api/public").permitAll()
        .mvcMatchers("/api/private").authenticated()
        .mvcMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages")
        .and()
        .oauth2ResourceServer().jwt()
  }

  @Bean
  fun jwtDecoder(): JwtDecoder {
    val jwtDecoder: NimbusJwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder

    val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
    val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
    val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)

    jwtDecoder.setJwtValidator(withAudience)
    return jwtDecoder
  }
}