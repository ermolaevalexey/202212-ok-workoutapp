package ok.workoutapp.workout.app.ktor.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
//import ru.otus.otuskotlin.workoutapp.common.stubs.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.*

suspend fun ApplicationCall.readWorkout() {
  val request = receive<WorkoutReadRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
  ctx.workoutReadResponse = WktWorkout(
    id = WktWorkoutId("128"),
    title = "Тренировка на плечи",
    description = "Просто подвигать плечами",
    type = WktWorkoutType.ARMS,
    equipment = WktEquipment.DUMBBELLS,
    content = WktWorkoutContent(
      video = "https://some-static/video/123",
      steps = mutableListOf(
        WktWorkoutStep(title = "Поднимите вверх плечи", description = "Это важно", timeCode = "00:15"),
        WktWorkoutStep(title = "Опустите вниз плечи", description = "Еще более важно", timeCode = "00:30"),
        WktWorkoutStep(title = "Cнова поднимите вверх плечи", description = "Архиважно", timeCode = "01:17")
      )
    ),
    rating = 4.5
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())


}