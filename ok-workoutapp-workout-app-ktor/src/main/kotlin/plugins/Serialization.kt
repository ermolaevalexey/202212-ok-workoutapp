package ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins

import io.ktor.serialization.jackson.jackson
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1Mapper

fun Application.configureSerialization() {
  install(ContentNegotiation) {
    jackson {
      setConfig(apiV1Mapper.serializationConfig)
      setConfig(apiV1Mapper.deserializationConfig)
    }
  }
  routing {
    get("/json/kotlinx-serialization") {
      call.respond(mapOf("hello" to "world"))
    }
  }
}