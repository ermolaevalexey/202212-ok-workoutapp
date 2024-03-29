package ru.otus.otuskotlin.workoutapp.common.models

data class WktError (
  val code: String = "",
  val group: String = "",
  val field: String = "",
  val message: String = "",
  val level: String = "",
  val exception: Throwable? = null,
)