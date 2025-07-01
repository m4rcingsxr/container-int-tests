package com.example

import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorDetailDto(

  val code: String,

  val message: String,

  val moreInfo: String? = null
)
