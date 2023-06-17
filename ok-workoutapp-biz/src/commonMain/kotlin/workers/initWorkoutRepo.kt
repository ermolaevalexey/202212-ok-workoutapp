package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.helpers.errorAdministration
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.helpers.fail
import ru.otus.otuskotlin.workoutapp.repoInMemory.IFeedbackRepository
import ru.otus.otuskotlin.workoutapp.repoInMemory.IWorkoutRepository
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext
import ru.otus.otuskotlin.workoutapp.workout.common.helpers.fail

fun ICorChainDsl<WktWorkoutContext>.initRepo(title: String) = worker {
  this.title = title
  description = "Вычисление репозитория тренировок на основании режима работы"
  handle {
    workoutRepo = when (workMode) {
      WktWorkMode.TEST -> settings.workoutRepoTest
      WktWorkMode.STUB -> settings.workoutRepoStub
      else -> settings.workoutRepoProd
    }
    if (workMode != WktWorkMode.STUB && workoutRepo == IWorkoutRepository.NONE) fail(
      errorAdministration(
        field = "repo",
        violationCode = "dbNotConfigured",
        description = "Не настроен репозиторий для $workMode"
      )
    )
  }
}