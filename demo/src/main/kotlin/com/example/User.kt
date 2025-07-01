package com.example

import io.quarkus.security.jpa.Password
import io.quarkus.security.jpa.Roles
import io.quarkus.security.jpa.UserDefinition
import io.quarkus.security.jpa.Username
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@UserDefinition
@Table(name = "users")
data class User(

  @Username
  val username: String,

  @Password
  val password: String,

  @Roles
  @Convert(converter = RoleSetConverter::class)
  val role: Set<Role>

) {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
   val id: Long? = null
}
