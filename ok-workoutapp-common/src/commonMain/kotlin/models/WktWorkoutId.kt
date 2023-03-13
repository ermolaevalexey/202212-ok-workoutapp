package ru.otus.otuskotlin.workoutapp.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WktWorkoutId(private val id: String) {
  fun asString() = id

  companion object {
    public val NONE = WktWorkoutId("")
  }
}