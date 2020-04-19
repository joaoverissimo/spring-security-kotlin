package com.verissimo.security.securityauth0.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MainController {

  @GetMapping(value = ["/public"])
  fun publicEndpoint(): Message {
    return Message("All good. You DO NOT need to be authenticated to call /api/public.")
  }

  @GetMapping(value = ["/private"])
  fun privateEndpoint(): Message {
    return Message("All good. You can see this because you are Authenticated.")
  }

  @GetMapping(value = ["/private-scoped"])
  fun privateScopedEndpoint(): Message {
    return Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope")
  }

}

data class Message(val message: String)