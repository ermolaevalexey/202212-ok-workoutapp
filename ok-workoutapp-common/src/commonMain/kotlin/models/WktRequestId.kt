package ru.otus.otuskotlin.workoutapp.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WktRequestId(private val id: String) {
  fun asString() = id

  companion object {
    val NONE = WktRequestId("")
  }
}