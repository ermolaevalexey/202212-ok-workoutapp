package ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.workoutapp.biz.WktFeedbackProcessor
import ru.otus.otuskotlin.workoutapp.biz.WktWorkoutProcessor
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackCorSettings
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.WktAppSettings
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutCorSettings

fun Application.initAppSettings(): WktAppSettings {
  val workoutRepoProd = getDatabaseConfWorkout(WktDbType.PROD)
  val workoutRepoStub = getDatabaseConfWorkout(WktDbType.TEST)
  val workoutRepoTest = getDatabaseConfWorkout(WktDbType.TEST)

  val corSettingsWorkout = WktWorkoutCorSettings(
    workoutRepoProd = workoutRepoProd,
    workoutRepoStub = workoutRepoStub,
    workoutRepoTest = workoutRepoTest,
  )
  val corSettingsFeedback = WktFeedbackCorSettings(
    feedbackRepoProd = getDatabaseConfFeedback(workoutRepoProd),
    feedbackRepoStub = getDatabaseConfFeedback(workoutRepoStub),
    feedbackRepoTest = getDatabaseConfFeedback(workoutRepoTest)
  )
  return WktAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    corSettingsWorkout = corSettingsWorkout,
    corSettingsFeedback = corSettingsFeedback,
    processorWorkout = WktWorkoutProcessor(corSettingsWorkout),
    processorFeedback = WktFeedbackProcessor(corSettingsFeedback)
  )
}