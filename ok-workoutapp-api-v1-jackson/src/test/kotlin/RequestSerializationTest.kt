package ru.otus.otuskotlin.workoutapp.api.v1

import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
class RequestSerializationTest {
  private val createRequest = WorkoutCreateRequest(
    requestId = "123",
    debug = Debug(
      mode = RequestDebugMode.TEST,
      stub = RequestDebugStubs.BAD_TITLE
    ),
    workout = WorkoutBase(
      title = "Workout with Dumbbells",
      description = "Simple test workout",
      type = WorkoutType.ARMS,
      equipment = Equipment.DUMBBELLS,
      content = WorkoutBaseContent(
        video = "https://www.youtube.com/watch?v=2fCKd-BQOhw",
        steps = listOf<WorkoutStep>(
          WorkoutStep(
            title = "Поднимите гантели",
            description = "Описание шага"
          ),
          WorkoutStep(
            title = "Опустите гантели",
            description = "Описание отпуска гантелей"
          )
        )
      )
    )
  )

  private val searchRequest = WorkoutSearchRequest(
    requestId = "128",
    debug = Debug(
      mode = RequestDebugMode.TEST,
      stub = RequestDebugStubs.BAD_TITLE
    ),
    params = WorkoutSearchRequestPayload(
      groupBy = listOf(WorkoutSearchGroupBy.EQUIPMENT, WorkoutSearchGroupBy.WORKOUT_TYPE)
    )
  )

  @Test
  fun serializeCreateRequest() {
    val json = apiV1Mapper.writeValueAsString(createRequest)

    assertContains(json, Regex("\"requestId\":\\s*\"123\""))
    assertContains(json, Regex("\"title\":\\s*\"Workout with Dumbbells\""))
    assertContains(json, Regex("\"mode\":\\s*\"test\""))
    assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
    assertContains(json, Regex("\"requestType\":\\s*\"workoutCreate\""))
    assertContains(json, Regex("\"video\":\\s*\"https://www.youtube.com/watch\\?v=2fCKd-BQOhw\""))
  }

  @Test
  fun serializeSearchRequest() {
    val json = apiV1Mapper.writeValueAsString(searchRequest)

    assertContains(json, Regex("\"requestId\":\"128\""))
    assertContains(json, Regex("\"mode\":\"test\""))
    assertContains(json, Regex("\"stub\":\"badTitle\""))
    assertContains(json, Regex("\"requestType\":\\s*\"workoutSearch\""))
    assertContains(json, Regex("\"workoutType\":\\s*false"))
    assertContains(json, Regex("\"equipment\":\\s*true"))
  }

  @Test
  fun deserializeCreateRequest() {
    val json = apiV1Mapper.writeValueAsString(createRequest)
    val obj = apiV1Mapper.readValue(json, Request::class.java) as WorkoutCreateRequest

    assertEquals(createRequest, obj)
  }

  @Test
  fun deserializeSearchRequest() {
    val json = apiV1Mapper.writeValueAsString(searchRequest)
    val obj = apiV1Mapper.readValue(json, Request::class.java) as WorkoutSearchRequest

    assertEquals(searchRequest, obj)
  }
}