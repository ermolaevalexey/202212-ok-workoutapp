package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.common.models.*
import org.junit.Test
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

import kotlin.test.assertEquals
class MapperTestWorkout {

  private val responseWorkout = WktWorkout(
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
  @Test
  fun fromTransport() {
    val req = WorkoutCreateRequest(
      requestId = "123",
      debug = Debug(
        mode = RequestDebugMode.STUB,
        stub = RequestDebugStubs.SUCCESS
      ),
      workout = WorkoutBase(
        title = "Training with own weight",
        description = "Description",
        type = WorkoutType.LEGS,
        equipment = Equipment.OWN_WEIGHT,
        content = WorkoutBaseContent(
          video = "",
          steps = listOf(
            WorkoutStep(title = "Присядьте", description = null, timecode = "00:00"),
            WorkoutStep(title = "Поднимитесь", description = null, timecode = "00:10")
          )
        )
      )
    )

    val context = WktWorkoutContext()
    context.fromTransport(req)

    assertEquals(WktStub.SUCCESS, context.stubCase)
    assertEquals(WktWorkMode.STUB, context.workMode)
    assertEquals("Training with own weight", context.workoutCreateRequest.title)
    assertEquals(WktWorkoutType.LEGS, context.workoutCreateRequest.type)
    assertEquals(WktEquipment.OWN_WEIGHT, context.workoutCreateRequest.equipment)
  }

  @Test
  fun toTransport() {
    val context = WktWorkoutContext(
      requestId = WktRequestId("1234"),
      command = WktCommand.WORKOUT_READ,
      workoutReadResponse = responseWorkout,
      errors = mutableListOf(
        WktError(
          code = "err",
          group = "request",
          field = "title",
          message = "wrong title",
        )
      ),
      state = WktState.RUNNING
    )

    val res = context.toTransport() as WorkoutReadResponse

    assertEquals("1234", res.requestId)
    assertEquals(responseWorkout.id.asString(), res.workout?.id)
    assertEquals(responseWorkout.title, res.workout?.title)
    assertEquals(responseWorkout.description, res.workout?.description)
    assertEquals(WorkoutType.ARMS, res.workout?.type)
    assertEquals(Equipment.DUMBBELLS, res.workout?.equipment)
    assertEquals(1, res.errors?.size)
    assertEquals("err", res.errors?.firstOrNull()?.code)
    assertEquals("request", res.errors?.firstOrNull()?.group)
    assertEquals("title", res.errors?.firstOrNull()?.field)
    assertEquals("wrong title", res.errors?.firstOrNull()?.message)
  }
}