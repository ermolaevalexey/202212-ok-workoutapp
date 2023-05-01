package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ok.workoutapp.workout.app.ktor.controllers.*
import ok.workoutapp.workout.app.ktor.ws.wsPing

fun Application.configureRouting() {
  install(AutoHeadResponse)
  routing {
    get("/") {
      call.respondText("Hello KTOR WorkoutApp!")
    }

//    webSocket("ws/ping") {
//      wsPing()
//    }

    route("v1") {
      route("workouts") {
        post ("read") {
          call.readWorkout()
        }

        post ("search") {
          call.searchWorkouts()
        }

        post ("create") {
          call.createWorkout()
        }

        post ("update") {
          call.updateWorkout()
        }
      }

      route("feedback") {
        post ("read") {
          call.readFeedback()
        }

        post ("create") {
          call.createFeedback()
        }

        post ("update") {
          call.updateFeedback()
        }

        post ("delete") {
          call.deleteFeedback()
        }
      }
    }
  }
}