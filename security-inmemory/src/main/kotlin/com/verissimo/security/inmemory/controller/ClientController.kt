package com.verissimo.security.inmemory.controller

import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/client")
class ClientController {

  @GetMapping
  @Secured("ROLE_CLIENT")
  fun get(@AuthenticationPrincipal user: User): String = "hello ${user.username}"

}