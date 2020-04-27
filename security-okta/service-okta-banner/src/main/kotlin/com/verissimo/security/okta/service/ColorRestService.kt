package com.verissimo.security.okta.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

data class ColorItem(val red: Int, val blue: Int, val green: Int)

interface ColorRestService {
  @GET("/api/colors")
  fun getColor(@Header("Authorization") authorization: String): Call<ColorItem>
}

@Configuration
class ColorRestConfig(
    val objectMapper: ObjectMapper,
    
    @Value("\${color-service-url}")
    val colorServiceUrl: String
) {

  @Bean
  fun colorRestClient(): ColorRestService {
    val retrofit = Retrofit.Builder()
        .baseUrl(colorServiceUrl)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()

    return retrofit.create(ColorRestService::class.java)
  }
}
