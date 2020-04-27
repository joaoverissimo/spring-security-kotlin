package com.verissimo.security.okta.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.Color

@RestController
@RequestMapping(path = ["api/colors"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MainController {

  @GetMapping
  fun privateEndpoint(): ColorItem {
    return listOfItems.random()
  }

  companion object {
    val listOfColors = listOf(Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK, Color.RED,
        Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.BLUE)

    val listOfItems = listOfColors
        .map { ColorItem(it.red, it.blue, it.green) }
  }
}

data class ColorItem(val red: Int, val blue: Int, val green: Int)