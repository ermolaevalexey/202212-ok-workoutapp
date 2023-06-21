package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.helpers.fail

import ru.otus.otuskotlin.workoutapp.common.helpers.errorAdministration
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.repoInMemory.IWorkoutRepository

fun ICorChainDsl<WktFeedbackContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        feedbackRepo = when (workMode) {
            WktWorkMode.STUB -> settings.feedbackRepoStub
            WktWorkMode.TEST -> settings.feedbackRepoTest
            else -> settings.feedbackRepoProd
        }
        if (workMode !== WktWorkMode.STUB && feedbackRepo == IWorkoutRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}