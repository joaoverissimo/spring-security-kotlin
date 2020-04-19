package com.verissimo.security.inmemory.controller

import com.verissimo.security.inmemory.config.SecurityConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerMockMvcTest {

  protected lateinit var mockMvc: MockMvc

  @Autowired
  protected lateinit var context: WebApplicationContext

  @BeforeEach
  fun setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply<DefaultMockMvcBuilder>(springSecurity())
        .build()
  }

  @Test
  fun isOk() {
    println(context.getBean(SecurityConfig::class.java))
  }

  @Test
  fun `should return unauthorized if not logged`() {

    mockMvc.perform(get("/api/client"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isUnauthorized)
  }

  @Test
  @WithMockUser(value = "verissimo", roles = ["USER", "CLIENT"])
  fun `should return 200 for verissimo user`() {

    mockMvc.perform(
        get("/api/client"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk)
        .andExpect(content().string("hello verissimo"))
  }

  @Test
  @WithMockUser(value = "demo", roles = ["USER"])
  fun `should deny for demo user`() {

    mockMvc.perform(
        get("/api/client"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isForbidden)
  }

  @Test
  fun `should return 200 for new user with role client`() {

    mockMvc.perform(
        get("/api/client")
            .with(user("new_user").password("secret").roles("CLIENT"))
    )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk)
        .andExpect(content().string("hello new_user"))
  }

}