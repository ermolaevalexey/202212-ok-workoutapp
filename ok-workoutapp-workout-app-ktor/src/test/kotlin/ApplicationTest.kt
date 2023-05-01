package ru.otus.otuskotlin.workoutapp.app.ktor

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import io.ktor.server.websocket.*
import ok.workoutapp.workout.app.ktor.plugins.configureHTTP
import ok.workoutapp.workout.app.ktor.plugins.configureRouting
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {
  @Test
  fun `root endpoint`() = testApplication {
    application {
      configureHTTP()
      configureRouting()
    }
    val response = client.get("/")
    assertEquals(HttpStatusCode.OK, response.status)
    assertEquals("Hello KTOR WorkoutApp!", response.bodyAsText())
  }
}