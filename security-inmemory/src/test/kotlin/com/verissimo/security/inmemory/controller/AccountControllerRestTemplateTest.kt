package com.verissimo.security.inmemory.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerRestTemplateTest {

  @Autowired
  protected lateinit var template: TestRestTemplate

  @Test
  fun `should return unauthorized if not logged`() {
    val response = template.getForEntity<String>("/api/client", String::class)

    assertThat(response.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
  }

  @Test
  fun `should allow users with rule CLIENT`() {
    val response = template.withBasicAuth("verissimo", "secret")
        .getForEntity<String>("/api/client", String::class)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
  }

  @Test
  fun `should deny users without rule CLIENT`() {
    val response = template.withBasicAuth("demo", "demo")
        .getForEntity<String>("/api/client", String::class)

    assertThat(response.statusCode).isEqualTo(HttpStatus.FORBIDDEN)
  }

}