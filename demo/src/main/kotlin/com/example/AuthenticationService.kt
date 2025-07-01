package com.example

import io.smallrye.jwt.build.Jwt.claims
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.SecurityContext

@ApplicationScoped
class AuthenticationService(
  private val userRepository: UserRepository
) {

  fun authenticate(
    securityContext: SecurityContext
  ): Uni<AuthenticationResponse> {
    val username = securityContext.userPrincipal.name
    val roles = getUserRoles(securityContext)

    return userRepository.findByUsername(username).map { user ->
      claims()
        .subject(username)
        .groups(roles)
        .sign()
        .let { AuthenticationResponse(accessToken = it) }
    }
  }

  private fun getUserRoles(
    securityContext: SecurityContext
  ): Set<String> = Role.entries
    .mapNotNull { role -> role.name.takeIf { securityContext.isUserInRole(it) } }
    .toSet()
}
