package ru.otus.otuskotlin.workoutapp.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WktUserId(private val id: String) {
  fun asString() = id

  companion object {
    public val NONE = WktUserId("")
  }
}
