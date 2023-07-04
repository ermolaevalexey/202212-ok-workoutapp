package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.common.helpers.errorAdministration
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.workout.common.repo.IWorkoutRepository
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext
import ru.otus.otuskotlin.workoutapp.workout.common.helpers.fail

fun ICorChainDsl<WktWorkoutContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        workoutRepo = when (workMode) {
            WktWorkMode.STUB -> settings.workoutRepoStub
            WktWorkMode.TEST -> settings.workoutRepoTest
            else -> settings.workoutRepoProd
        }
        if (workMode !== WktWorkMode.STUB && workoutRepo == IWorkoutRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}