package com.example

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepository<User> {

  fun findByUsername(
    username: String
  ): Uni<User> = find("username", username).singleResult()
}
