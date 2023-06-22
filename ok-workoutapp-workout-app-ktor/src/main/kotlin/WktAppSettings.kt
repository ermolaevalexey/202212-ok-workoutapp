package ok.workoutapp.workout.app.ktor

import WktCorSettings
import ru.otus.otuskotlin.workoutapp.biz.WktFeedbackProcessor
import ru.otus.otuskotlin.workoutapp.biz.WktWorkoutProcessor

data class WktAppSettings(
  val appUrls: List<String> = emptyList(),
  val corSettings: WktCorSettings,
  val processorWorkout: WktWorkoutProcessor = WktWorkoutProcessor(settings = corSettings),
  val processorFeedback: WktFeedbackProcessor = WktFeedbackProcessor(settings = corSettings)
  )
