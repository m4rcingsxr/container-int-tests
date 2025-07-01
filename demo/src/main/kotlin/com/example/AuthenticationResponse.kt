package com.example

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
  val accessToken: String
)
