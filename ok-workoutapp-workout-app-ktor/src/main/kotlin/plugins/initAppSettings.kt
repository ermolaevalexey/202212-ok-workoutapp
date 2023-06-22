package ok.workoutapp.workout.app.ktor.plugins

import WktCorSettings
import io.ktor.server.application.*
import ok.workoutapp.workout.app.ktor.WktAppSettings
import ru.otus.otuskotlin.workoutapp.biz.WktFeedbackProcessor
import ru.otus.otuskotlin.workoutapp.biz.WktWorkoutProcessor
import ru.otus.otuskotlin.workoutapp.repoInMemory.WorkoutRepoInMemory

fun Application.initAppSettings(): WktAppSettings {
  val workoutRepoProd = getDatabaseConfWorkout()
  val workoutRepoStub = getDatabaseConfWorkout()
  val workoutRepoTest = getDatabaseConfWorkout()
  val corSettings = WktCorSettings(
    workoutRepoProd = workoutRepoProd,
    workoutRepoStub = workoutRepoStub,
    workoutRepoTest = workoutRepoTest,
    feedbackRepoProd = getDatabaseConfFeedback(workoutRepoProd as WorkoutRepoInMemory),
    feedbackRepoStub = getDatabaseConfFeedback(workoutRepoStub as WorkoutRepoInMemory),
    feedbackRepoTest = getDatabaseConfFeedback(workoutRepoTest as WorkoutRepoInMemory)
  )
  return WktAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    corSettings = corSettings,
    processorWorkout = WktWorkoutProcessor(corSettings),
    processorFeedback = WktFeedbackProcessor(corSettings)
  )
}