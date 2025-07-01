package com.example

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
internal class RoleSetConverter : AttributeConverter<Set<Role>, String> {
  override fun convertToDatabaseColumn(attribute: Set<Role>): String =
    attribute.joinToString(separator = ",") { it.name }

  override fun convertToEntityAttribute(dbData: String): Set<Role> =
    dbData.split(",").map { roleString ->
      runCatching { Role.valueOf(value = roleString) }
        .getOrElse { throw InvalidRoleException(invalidRole = roleString) }
    }.toSet()
}
