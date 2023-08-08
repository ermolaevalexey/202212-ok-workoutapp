package ru.otus.otuskotlin.workoutapp.common.models

enum class WktUserRole {
  USER,
  ADMIN;

  companion object {
    public fun fromValue(value: String): WktUserRole = when (value) {
      "user" -> USER
      "admin"  -> ADMIN
      else         -> throw IllegalArgumentException()
    }

    public fun toValue(value: WktUserRole): String = when (value) {
      USER -> "user"
      ADMIN -> "admin"
      else         -> throw IllegalArgumentException()
    }
  }
}