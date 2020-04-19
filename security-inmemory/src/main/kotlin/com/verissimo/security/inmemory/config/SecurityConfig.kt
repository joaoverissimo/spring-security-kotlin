package com.verissimo.security.inmemory.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {


  public override fun configure(http: HttpSecurity?) {
    http?.antMatcher("/api/**")
        ?.authorizeRequests()
        //?.antMatchers("/api/client**")?.hasRole("CLIENT")
        //?.antMatchers("/api/account**")?.hasRole("ACCOUNT")
        ?.anyRequest()?.authenticated()
        ?.and()?.httpBasic()
  }

  public override fun configure(builder: AuthenticationManagerBuilder) {
    builder.inMemoryAuthentication()
        .withUser("verissimo").password("{noop}secret").roles("ACCOUNT", "CLIENT").and()
        .withUser("demo").password("{noop}demo").roles("ACCOUNT")
  }


}