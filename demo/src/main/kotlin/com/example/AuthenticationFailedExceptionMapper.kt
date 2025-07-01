package com.example

import io.quarkus.security.AuthenticationFailedException
import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities.AUTHENTICATION
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status.UNAUTHORIZED
import jakarta.ws.rs.core.Response.status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
@Priority(AUTHENTICATION)
internal class AuthenticationFailedExceptionMapper : ExceptionMapper<AuthenticationFailedException> {

  override fun toResponse(
    exception: AuthenticationFailedException
  ): Response {
    val error = ErrorDto().addDetail(
      ErrorDetailDto(
        code = "401",
        message = "Authentication required or credentials are invalid"
      )
    )

    return status(UNAUTHORIZED)
      .entity(error)
      .type(APPLICATION_JSON)
      .build()
  }
}
