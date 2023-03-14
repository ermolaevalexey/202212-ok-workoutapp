package ru.otus.otuskotlin.workoutapp.mappers.v1

import WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.common.models.*
import org.junit.Test
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser
import kotlin.test.assertEquals
class MapperTestFeedback {

  private val responseFeedback = WktFeedback(
    id = WktFeedbackId("128"),
    workout = WktWorkoutId("wkt-185"),
    review = "Тяжеловато!",
    user = WktFeedbackUser(
      name = "Серсея Ланнистер",
      id = WktUserId("usr345")
    ),
    rating = 2.3
  )
  @Test
  fun fromTransport() {
    val req = FeedbackCreateRequest(
      requestId = "123",
      debug = Debug(
        mode = RequestDebugMode.STUB,
        stub = RequestDebugStubs.SUCCESS
      ),
      feedback = FeedbackCreateRequestPayload(
        workout = "wkt-456",
        user = "usr-577",
        review = "Тренировка была настолько тяжелой, что я думал, что не смогу выжить. Но я сделал это!",
        rating = 4.5
      )
    )

    val context = WktFeedbackContext()
    context.fromTransport(req)

    assertEquals(WktStub.SUCCESS, context.stubCase)
    assertEquals(WktWorkMode.STUB, context.workMode)
    assertEquals(WktWorkoutId("wkt-456"), context.feedbackCreateRequest.workout)
    assertEquals(WktUserId("usr-577"), context.feedbackCreateRequest.user)
    assertEquals(4.5, context.feedbackCreateRequest.rating)
  }

  @Test
  fun toTransport() {
    val context = WktFeedbackContext(
      requestId = WktRequestId("1234"),
      command = WktCommand.FEEDBACK_READ,
      feedbackReadResponse = mutableListOf(responseFeedback),
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

    val res = context.toTransport() as FeedbackReadResponse

    assertEquals("1234", res.requestId)
    assertEquals(responseFeedback.workout?.asString(), res.feedback?.get(0)?.workout)
    assertEquals(responseFeedback.review, res.feedback?.get(0)?.review)
    assertEquals(responseFeedback.user?.name, res.feedback?.get(0)?.user?.name)
    assertEquals(1, res.errors?.size)
    assertEquals("err", res.errors?.firstOrNull()?.code)
    assertEquals("request", res.errors?.firstOrNull()?.group)
    assertEquals("title", res.errors?.firstOrNull()?.field)
    assertEquals("wrong title", res.errors?.firstOrNull()?.message)
  }
}