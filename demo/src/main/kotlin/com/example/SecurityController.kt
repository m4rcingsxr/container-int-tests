package com.example

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.SecurityContext

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/security")
class SecurityController(
  private val authenticationService: AuthenticationService
) {

  @POST
  @WithSession
  @Path("/login")

  fun login(
    @Context securityContext: SecurityContext
  ): Uni<AuthenticationResponse> {
    return authenticationService.authenticate(securityContext)

  }
}
