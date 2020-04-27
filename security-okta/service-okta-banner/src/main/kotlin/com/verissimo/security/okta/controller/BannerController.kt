package com.verissimo.security.okta.controller

import com.verissimo.security.okta.service.ColorItem
import com.verissimo.security.okta.service.ColorRestService
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping(path = ["api/banner"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BannerController(
    val colorRestClient: ColorRestService,
    val clientService: OAuth2AuthorizedClientService
) {

  @GetMapping
  fun publicEndpoint(principal: Principal): Banner {
    val jwtToken = principal as JwtAuthenticationToken

    val auth = "Bearer ${jwtToken.token.tokenValue}"
    val color = colorRestClient.getColor(auth).execute().body()
    checkNotNull(color, { "Error while getting color" })

    return Banner("Banner created", color)
  }

  @PostMapping
  fun dfsdfsa() {

  }

}

data class Banner(val info: String, val color: ColorItem)