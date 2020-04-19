package com.verissimo.security.inmemory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityApplication

fun main(args: Array<String>) {
  runApplication<SecurityApplication>(*args)
}
