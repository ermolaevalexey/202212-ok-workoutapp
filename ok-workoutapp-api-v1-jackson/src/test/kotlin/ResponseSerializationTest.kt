package ru.otus.otuskotlin.workoutapp.api.v1

import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
  private val workout1 = Workout(
    id = "567",
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
      ),
    ),
    rating = 0.0,
    feedback = emptyList()
  )

  private val workout2 = Workout(
    id = "567",
    title = "Workout with Bars",
    description = "Simple test bars",
    type = WorkoutType.ARMS,
    equipment = Equipment.BARS,
    content = WorkoutBaseContent(
      video = "https://www.youtube.com/watch?v=2fCKd-BQOhw",
      steps = listOf<WorkoutStep>(
        WorkoutStep(
          title = "Подтянитесь",
          description = "Описание шага"
        ),
        WorkoutStep(
          title = "Опуститесь",
          description = "Описание отпуска гантелей"
        )
      ),
    ),
    rating = 5.0,
    feedback = listOf(
      Feedback(
        user = FeedbackUser(id = "1234-456", name = "Василий Пупкин"),
        rating = 4.5,
        review = "Очень хорошая тренировка"
      ),
      Feedback(
        user = FeedbackUser(id = "1234-456", name = "Серсея Ланнистер"),
        rating = 2.5,
        review = "Слишком тяжело"
      )
    )
  )


  private val createResponse = WorkoutCreateResponse(
    requestId = "123",
    responseType = "workoutCreate",
    result = Result.SUCCESS,
    errors = emptyList<Error>(),
    workout = workout1
  )

  private val searchResponse = WorkoutSearchResponse(
    requestId = "128",
    responseType = "workoutSearch",
    result = Result.SUCCESS,
    workout = WorkoutSearchResponsePayload(
      groups = listOf(
        WorkoutSearchResponsePayloadGroupsInner(
          groupName = Equipment.DUMBBELLS.toString(),
          workouts = listOf(workout1)
        ),
        WorkoutSearchResponsePayloadGroupsInner(
          groupName = Equipment.BARS.toString(),
          workouts = listOf(workout2)
        )
      )
    )
  )

  @Test
  fun serializeCreateResponse() {
    val json = apiV1Mapper.writeValueAsString(createResponse)

    assertContains(json, Regex("\"requestId\":\\s*\"123\""))
    assertContains(json, Regex("\"id\":\\s*\"567\""))
    assertContains(json, Regex("\"title\":\\s*\"Workout with Dumbbells\""))

    assertContains(json, Regex("\"responseType\":\\s*\"workoutCreate\""))
  }

  @Test
  fun serializeSearchResponse() {
    val json = apiV1Mapper.writeValueAsString(searchResponse)

    assertContains(json, Regex("\"requestId\":\"128\""))
    assertContains(json, Regex("\"title\":\\s*\"Workout with Bars\""))
    assertContains(json, Regex("\"responseType\":\\s*\"workoutSearch\""))
    assertContains(json, Regex("\"groupName\":\\s*\"Dumbbells\""))
  }

  @Test
  fun deserializeCreateResponse() {
    val json = apiV1Mapper.writeValueAsString(createResponse)
    val obj = apiV1Mapper.readValue(json, Response::class.java) as WorkoutCreateResponse

    assertEquals(createResponse, obj)
  }

  @Test
  fun deserializeSearchResponse() {
    val json = apiV1Mapper.writeValueAsString(searchResponse)
    val obj = apiV1Mapper.readValue(json, Response::class.java) as WorkoutSearchResponse

    assertEquals(searchResponse, obj)
  }
}