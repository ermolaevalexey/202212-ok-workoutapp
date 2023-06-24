package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ok.workoutapp.workout.app.ktor.WktAppSettings
import ok.workoutapp.workout.app.ktor.controllers.*
//import ok.workoutapp.workout.app.ktor.ws.wsPing

fun Application.configureRouting(appSettings: WktAppSettings) {
  install(AutoHeadResponse)
  install(CORS) {
    allowNonSimpleContentTypes = true
    allowSameOrigin = true
    allowMethod(HttpMethod.Options)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Get)
    allowHeader("*")
    appSettings.appUrls.forEach {
      val split = it.split("://")
      println("$split")
      when (split.size) {
        2 -> allowHost(
          split[1].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
          listOf(split[0])
        )

        1 -> allowHost(
          split[0].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
          listOf("http", "https")
        )
      }
    }
  }
  install(CachingHeaders)
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
          call.readWorkout(appSettings)
        }

        post ("search") {
          call.searchWorkouts(appSettings)
        }

        post ("create") {
          call.createWorkout(appSettings)
        }

        post ("update") {
          call.updateWorkout(appSettings)
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