package ru.otus.otuskotlin.workoutapp.feedback.common

import ru.otus.otuskotlin.workoutapp.feedback.common.repo.IFeedbackRepository

data class WktFeedbackCorSettings(
  val feedbackRepoStub: IFeedbackRepository = IFeedbackRepository.NONE,
  val feedbackRepoTest: IFeedbackRepository = IFeedbackRepository.NONE,
  val feedbackRepoProd: IFeedbackRepository = IFeedbackRepository.NONE
) {
  companion object {
    val NONE = WktFeedbackCorSettings()
  }
}
