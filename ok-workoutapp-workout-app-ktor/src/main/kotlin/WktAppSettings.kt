package ok.workoutapp.workout.app.ktor

import ru.otus.otuskotlin.workoutapp.biz.WktFeedbackProcessor
import ru.otus.otuskotlin.workoutapp.biz.WktWorkoutProcessor
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackCorSettings
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutCorSettings

data class WktAppSettings(
  val appUrls: List<String> = emptyList(),
  val corSettingsWorkout: WktWorkoutCorSettings,
  val corSettingsFeedback: WktFeedbackCorSettings,
  val processorWorkout: WktWorkoutProcessor = WktWorkoutProcessor(settings = corSettingsWorkout),
  val processorFeedback: WktFeedbackProcessor = WktFeedbackProcessor(settings = corSettingsFeedback)
  )
