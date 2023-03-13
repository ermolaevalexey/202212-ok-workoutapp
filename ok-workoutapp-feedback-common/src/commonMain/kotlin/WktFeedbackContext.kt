import kotlinx.datetime.Instant
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.feedback.common.models.*
data class WktFeedbackContext(
  var command: WktCommand = WktCommand.NONE,
  var state: WktState = WktState.NONE,
  var errors: MutableList<WktError> = mutableListOf(),

  var workMode: WktWorkMode = WktWorkMode.PROD,
  var stubCase: WktStub = WktStub.NONE,

  var requestId: WktRequestId = WktRequestId.NONE,
  var timeStart: Instant = Instant.NONE,

  var feedbackCreateRequest: WktFeedbackPayload = WktFeedbackPayload(),
  var feedbackUpdateRequest: WktFeedbackPayload = WktFeedbackPayload(),
  var feedbackReadRequest: WktWorkoutId = WktWorkoutId.NONE,
  var feedbackDeleteRequest: WktFeedbackPayload = WktFeedbackPayload(),

  var feedbackCreateResponse: WktFeedback = WktFeedback(),
  var feedbackReadResponse: MutableList<WktFeedback> = mutableListOf(),
  var feedbackUpdateResponse: WktFeedback = WktFeedback(),
  var feedbackDeleteResponse: WktFeedbackPayload = WktFeedbackPayload()
)