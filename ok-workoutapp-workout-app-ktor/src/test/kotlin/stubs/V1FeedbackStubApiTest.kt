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

class V1FeedbackStubApiTest {
  @Test
  fun create() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()

    val response = client.post("v1/feedback/create") {
      contentType(ContentType.Application.Json)

      val requestObj = FeedbackCreateRequest(
        requestId = "3456",
        feedback = FeedbackCreateRequestPayload(
          workout = "456",
          user = "user-849",
          review = "Good workout",
          rating = 4.7
        )
      )

      setBody(requestObj)
    }

    val responseObj = response.body<FeedbackCreateResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("777", responseObj.feedback?.id)
    assertEquals("user-849", responseObj.feedback?.user?.id)
    assertEquals("User user-849", responseObj.feedback?.user?.name)
    assertEquals(4.7, responseObj.feedback?.rating)
  }

  @Test
  fun update() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()

    val response = client.post("v1/feedback/update") {
      contentType(ContentType.Application.Json)

      val requestObj = FeedbackUpdateRequest(
        requestId = "124334",
        feedback = FeedbackUpdateRequestPayload(
          id = "128",
          review = "Very bad workout",
          rating = 2.7
        )
      )

      setBody(requestObj)
    }

    val responseObj = response.body<FeedbackUpdateResponse>()
    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("128", responseObj.feedback?.id)
    assertEquals("wkt-185", responseObj.feedback?.workout)
    assertEquals("usr345", responseObj.feedback?.user?.id)
    assertEquals("Серсея Ланнистер", responseObj.feedback?.user?.name)
    assertEquals("Very bad workout", responseObj.feedback?.review)
    assertEquals(2.7, responseObj.feedback?.rating)
  }

  @Test
  fun delete() = testApplication {
    application {
      configureRouting()
      configureHTTP()
      configureSerialization()
    }

    val client = myClient()

    val response = client.post("v1/feedback/delete") {
      contentType(ContentType.Application.Json)

      val requestObj = FeedbackDeleteRequest(
        requestId = "44335",
        feedback = FeedbackDeleteRequestPayload(
          id = "128",
          user = "usr345"
        )
      )

      setBody(requestObj)
    }

    val responseObj = response.body<FeedbackDeleteResponse>()

    println(responseObj)
    assertEquals(200, response.status.value)
    assertEquals("128", responseObj.feedback?.id)
    assertEquals("wkt-185", responseObj.feedback?.workout)
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