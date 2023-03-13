import ru.otus.otuskotlin.workoutapp.api.v1.models.Debug
import ru.otus.otuskotlin.workoutapp.api.v1.models.Request
import ru.otus.otuskotlin.workoutapp.api.v1.models.RequestDebugMode
import ru.otus.otuskotlin.workoutapp.api.v1.models.RequestDebugStubs
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub

fun Request?.requestId() = this?.requestId?.let { WktRequestId(it) } ?: WktRequestId.NONE
fun String?.toWktWorkoutId() = this?.let { WktWorkoutId(it) } ?: WktWorkoutId.NONE
fun String?.toWktUserId() = this?.let { WktUserId(it) } ?: WktUserId.NONE
fun String?.toWktFeedbackId() = this?.let { WktFeedbackId(it) } ?: WktFeedbackId.NONE
fun Debug?.fromTransportToWorkMode() = when (this?.mode) {
  RequestDebugMode.PROD -> WktWorkMode.PROD
  RequestDebugMode.TEST -> WktWorkMode.TEST
  RequestDebugMode.STUB -> WktWorkMode.STUB
  null -> WktWorkMode.NONE
}

fun Debug?.fromTransportToStubCase() = when (this?.stub) {
  RequestDebugStubs.SUCCESS -> WktStub.SUCCESS
  RequestDebugStubs.NOT_FOUND -> WktStub.NOT_FOUND
  RequestDebugStubs.BAD_ID -> WktStub.BAD_ID
  RequestDebugStubs.BAD_TITLE -> WktStub.BAD_TITLE
  RequestDebugStubs.BAD_DESCRIPTION -> WktStub.BAD_DESCRIPTION
  RequestDebugStubs.CANNOT_DELETE -> WktStub.CANNOT_DELETE
  RequestDebugStubs.EMPTY_SEARCH -> WktStub.EMPTY_SEARCH
  null -> WktStub.NONE
}