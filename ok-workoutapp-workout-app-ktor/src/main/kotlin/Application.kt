package ok.workoutapp.workout.app.ktor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ok.workoutapp.workout.app.ktor.plugins.*

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  configureSerialization()
  configureHTTP()
  configureRouting()
//  configureMonitoring()
//  configureSockets()
//  configureSecurity()
}
