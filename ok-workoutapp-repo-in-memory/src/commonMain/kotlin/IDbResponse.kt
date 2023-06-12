package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktError

interface IDbResponse<T> {
  val data: T?
  val isSuccess: Boolean
  val errors: List<WktError>
}