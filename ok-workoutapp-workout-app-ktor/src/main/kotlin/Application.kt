package ru.otus.otuskotlin.workoutapp.workout.app.ktor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins.*
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins.configureHTTP
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins.configureRouting
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins.configureSerialization

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module(appSettings: WktAppSettings = initAppSettings()) {
  configureSerialization()
  configureHTTP()
  configureRouting(appSettings)
}
