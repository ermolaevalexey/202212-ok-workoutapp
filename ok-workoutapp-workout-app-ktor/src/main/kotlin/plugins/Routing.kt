package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.application.*
import ok.workoutapp.workout.app.ktor.controllers.*

fun Application.configureRouting() {
  install(AutoHeadResponse)
  routing {
    get("/") {
      call.respondText("Hello KTOR WorkoutApp!")
    }

    route("workouts") {
      post ("read") {
        call.readWorkout()
      }
    }
  }
}