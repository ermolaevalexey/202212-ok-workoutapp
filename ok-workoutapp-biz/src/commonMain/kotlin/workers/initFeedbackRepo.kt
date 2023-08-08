package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.helpers.errorAdministration
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.helpers.fail
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.IFeedbackRepository

fun ICorChainDsl<WktFeedbackContext>.initRepo(title: String) = worker {
  this.title = title
  description = "Вычисление репозитория отзывов на основании режима работы"
  handle {
    feedbackRepo = when (workMode) {
      WktWorkMode.TEST -> settings.feedbackRepoTest
      WktWorkMode.STUB -> settings.feedbackRepoStub
      else -> settings.feedbackRepoProd
    }
    if (workMode != WktWorkMode.STUB && feedbackRepo == IFeedbackRepository.NONE) fail(
      errorAdministration(
        field = "repo",
        violationCode = "dbNotConfigured",
        description = "Не настроен репозиторий для $workMode"
      )
    )
  }
}