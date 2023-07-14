package ru.otus.otuskotlin.workoutapp.repo.postgres

open class SqlProperties(
  val url: String = "jdbc:postgresql://localhost:5432/workoutapp",
  val user: String = "postgres",
  val password: String = "workoutapp-pass",
  val schema: String = "workoutapp",
  // Удалять таблицы при старте - нужно для тестирования
  val dropDatabase: Boolean = false
)