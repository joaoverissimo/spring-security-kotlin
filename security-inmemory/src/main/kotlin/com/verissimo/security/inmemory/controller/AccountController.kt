package com.verissimo.security.inmemory.controller

import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/account")
class AccountController {

  @GetMapping
  @Secured("ROLE_ACCOUNT")
  fun get(@AuthenticationPrincipal user: User): String = "Here are your account details ${user.username}"

}