package com.example

import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorDto(

  val trace: String? = null
) {

  val errors: MutableList<ErrorDetailDto> = mutableListOf()

  fun addDetail(
    detail: ErrorDetailDto
  ): ErrorDto = apply {
    errors.add(detail)
  }
}
