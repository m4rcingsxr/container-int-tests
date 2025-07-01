package com.example

import io.quarkus.security.jpa.RolesValue

enum class Role {
  TAX_USER,
  TAX_MANAGER,
  TAX_ACCOUNTANT,
  TAX_ACCOUNTANT_MANAGER
  ;

  @RolesValue
  fun roleName(): String = name
}
