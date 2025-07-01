package com.example

internal class InvalidRoleException(invalidRole: String) : RuntimeException("Exception occurred when parsing Role value='$invalidRole'")
