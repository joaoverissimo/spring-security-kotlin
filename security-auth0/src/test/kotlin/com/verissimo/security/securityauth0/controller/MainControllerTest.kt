package com.verissimo.security.securityauth0.controller

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(MainController::class)
@ExtendWith(SpringExtension::class)
class APIControllerTest {

  @Autowired
  private lateinit var mockMvc: MockMvc

  @MockBean
  private val jwtDecoder: JwtDecoder? = null

  @Test
  fun testPublicEndpoint() {
    val mvcResult = mockMvc.perform(get("/api/public"))
        .andDo(print())
        .andExpect(status().isOk)
        .andReturn()

    assertNotNull(mvcResult.response.contentAsString)
  }

  @Test
  fun testPrivateEndpointReturnsUnauthorizedWhenNoUser() {
    mockMvc.perform(get("/api/private"))
        .andDo(print())
        .andExpect(status().isUnauthorized)
        .andReturn()
  }

  @Test
  @WithMockUser(username = "testUser")
  fun testPrivateEndpointReturnsOkWhenAuthorized() {
    val mvcResult = mockMvc.perform(get("/api/private"))
        .andDo(print())
        .andExpect(status().isOk)
        .andReturn()

    assertNotNull(mvcResult.response.contentAsString)
  }

  @Test
  fun testPrivateScopedEndpointReturnsUnauthorizedWhenNoUser() {
    mockMvc.perform(get("/api/private-scoped"))
        .andDo(print())
        .andExpect(status().isUnauthorized)
        .andReturn()
  }

  @Test
  @WithMockUser(username = "testUser")
  fun testPrivateScopedEndpointReturnsForbiddenWhenNoScopes() {
    mockMvc.perform(get("/api/private-scoped"))
        .andDo(print())
        .andExpect(status().isForbidden)
        .andReturn()
  }

  @Test
  @WithMockUser(username = "testUser", authorities = ["SCOPE_read:messages"])
  fun testPrivateScopedEndpointReturnsOkWhenProperScopes() {
    val mvcResult = mockMvc.perform(get("/api/private-scoped"))
        .andDo(print())
        .andExpect(status().isOk)
        .andReturn()

    assertNotNull(mvcResult.response.contentAsString)
  }

  @Test
  @WithMockUser(username = "testUser", authorities = ["SCOPE_write:messages"])
  fun testPrivateScopedEndpointReturnsForbiddenWhenIncorrectScopes() {
    mockMvc.perform(get("/api/private-scoped"))
        .andDo(print())
        .andExpect(status().isForbidden)
        .andReturn()
  }
}
