package ru.otus.otuskotlin.workoutapp.workout.app.ktor.configs

import io.ktor.server.config.*

data class PostgresConfig(
  val url: String = "jdbc:postgresql://localhost:5432/workoutapp",
  val user: String = "postgres",
  val password: String = "workoutapp-pass",
  val schema: String = "workoutapp",
) {
//  constructor(config: ApplicationConfig): this(
//    url = config.property("$PATH.url").getString(),
//    user = config.property("$PATH.user").getString(),
//    password = config.property("$PATH.password").getString(),
//    schema = config.property("$PATH.schema").getString(),
//  )

  companion object {
    const val PATH = "${ConfigPaths.repository}.psql"
  }
}