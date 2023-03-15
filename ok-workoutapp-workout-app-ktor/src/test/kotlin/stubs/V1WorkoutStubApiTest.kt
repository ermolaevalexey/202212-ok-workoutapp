package ru.otus.otuskotlin.workoutapp.app.ktor.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ok.workoutapp.workout.app.ktor.plugins.configureHTTP
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ok.workoutapp.workout.app.ktor.plugins.configureRouting
import ok.workoutapp.workout.app.ktor.plugins.configureSerialization
import org.junit.Test
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1Mapper
import kotlin.test.assertEquals

class V1WorkoutStubApiTest {
  @Test
  fun create() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }
    val client = myClient()
    val response = client.post("v1/workouts/create") {
      contentType(ContentType.Application.Json)

      val requestObj = WorkoutCreateRequest(
        requestId = "12345",
        workout = WorkoutBase(
          title = "Wkt Dumbbells 1",
          description = "Desc Wkt Dumbbells 1",
          type = WorkoutType.ARMS,
          equipment = Equipment.DUMBBELLS,
          content = WorkoutBaseContent(
            video = "https://some-training.com/111",
            steps = listOf(
              WorkoutStep(
                title = "Step 1",
                description = "Desc Step 1",
                timecode = "00:10"
              ),
              WorkoutStep(
                title = "Step 2",
                description = "Desc Step 2",
                timecode = "01:15"
              ),
              WorkoutStep(
                title = "Step 3",
                description = "Desc Step 3",
                timecode = "02:57"
              )
            )
          )
        ),
        debug = Debug(
          mode = RequestDebugMode.STUB,
          stub = RequestDebugStubs.SUCCESS
        )
      )

      setBody(requestObj)
    }

    val responseObj = response.body<WorkoutCreateResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("555", responseObj.workout?.id)
  }
  @Test
  fun update() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()
    val response = client.post("v1/workouts/update") {
      contentType(ContentType.Application.Json)
      val requestObj = WorkoutUpdateRequest(
        requestId = "78910",
        workout = WorkoutUpdateRequestPayloadWorkout(
          id = "777",
          title = "Руки не для скуки"
        )
      )
      setBody(requestObj)
    }

    val responseObj = response.body<WorkoutUpdateResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("777", responseObj.workout?.id)
    assertEquals("Руки не для скуки", responseObj.workout?.title)
  }

  @Test
  fun read() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()

    val response = client.post("v1/workouts/read") {
      contentType(ContentType.Application.Json)

      val requestObj = WorkoutReadRequest(
        requestId = "999",
        workout = WorkoutReadRequestPayloadWorkout(
          id = "777"
        )
      )

      setBody(requestObj)
    }

    val responseObj = response.body<WorkoutReadResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("777", responseObj.workout?.id)
    assertEquals("Рельефные руки", responseObj.workout?.title)
  }

  @Test
  fun search() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()

    val response = client.post("v1/workouts/search") {
      contentType(ContentType.Application.Json)
      val requestObj = WorkoutSearchRequest(
        requestId = "455",
        params = WorkoutSearchRequestPayload(
          groupBy = listOf(WorkoutSearchGroupBy.WORKOUT_TYPE, WorkoutSearchGroupBy.EQUIPMENT)
        )
      )
      setBody(requestObj)
    }

    val responseObj = response.body<WorkoutSearchResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals(8, responseObj.workout?.groups?.size)
  }

  private fun ApplicationTestBuilder.myClient() = createClient {
    install(ContentNegotiation) {
      jackson {
        setConfig(apiV1Mapper.serializationConfig)
        setConfig(apiV1Mapper.deserializationConfig)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        enable(SerializationFeature.INDENT_OUTPUT)
        writerWithDefaultPrettyPrinter()
      }
    }
  }
}