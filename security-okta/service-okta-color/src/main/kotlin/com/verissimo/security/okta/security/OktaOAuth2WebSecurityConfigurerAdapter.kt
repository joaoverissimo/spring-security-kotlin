package com.verissimo.security.okta.security

import com.okta.spring.boot.oauth.Okta
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


class OktaOAuth2WebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.authorizeRequests()
        .anyRequest().fullyAuthenticated()
        .and()
        .oauth2ResourceServer().jwt()

    Okta.configureResourceServer401ResponseBody(http);
  }

}