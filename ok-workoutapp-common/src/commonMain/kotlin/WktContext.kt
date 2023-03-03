import kotlinx.datetime.Instant
import models.*

data class WktContext(
  var command: WktCommand = WktCommand.NONE,
  var state: WktState = WktState.NONE,
  var errors: MutableList<WktError> = mutableListOf(),

  var workMode: WktWorkMode = WktWorkMode.PROD,
  var stubCase: WktStub = WktStub.NONE,

  var requestId: WktRequestId = WktRequestId.NONE,
  var timeStart: Instant = Instant.NONE,

  var workoutReadRequest: WktWorkoutId = WktWorkoutId.NONE,
  var workoutReadResponse: WktWorkout = WktWorkout(),
  var workoutSearchRequest: WktWorkoutSearch = WktWorkoutSearch(),
  var workoutSearchResponse: MutableList<WktWorkoutSearchResult> = mutableListOf(),

)