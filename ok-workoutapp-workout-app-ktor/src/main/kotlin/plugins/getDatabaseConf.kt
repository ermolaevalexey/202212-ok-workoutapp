package ru.otus.otuskotlin.workoutapp.workout.app.ktor.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.configs.PostgresConfig
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.IFeedbackRepository
import ru.otus.otuskotlin.workoutapp.repo.postgres.RepoWorkoutSql
import ru.otus.otuskotlin.workoutapp.repo.postgres.SqlProperties
import ru.otus.otuskotlin.workoutapp.repoInMemory.FeedbackRepoInMemory
import ru.otus.otuskotlin.workoutapp.repoInMemory.WorkoutRepoInMemory
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.configs.ConfigPaths
import ru.otus.otuskotlin.workoutapp.workout.common.repo.IWorkoutRepository
import kotlin.time.Duration.Companion.minutes

actual fun Application.getDatabaseConfWorkout(type: WktDbType): IWorkoutRepository {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemoryWorkout()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgresWorkout()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                "'inmemory', 'postgres'"
        )
    }
}

fun Application.getDatabaseConfFeedback(wktRepo: IWorkoutRepository): IFeedbackRepository {
    return initInMemoryFeedback(wktRepo)
}

private fun Application.initInMemoryWorkout(): WorkoutRepoInMemory {
    return WorkoutRepoInMemory(ttl = 10.minutes)
}

private fun Application.initPostgresWorkout(): IWorkoutRepository {
    val config = PostgresConfig(environment.config)
    return RepoWorkoutSql(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}

private fun Application.initInMemoryFeedback(wktRepo: IWorkoutRepository): IFeedbackRepository {
    return FeedbackRepoInMemory(ttl = 10.minutes, wktRepo = wktRepo)
}


enum class WktDbType(val confName: String) {
    PROD("prod"),
    TEST("test")
}
expect fun Application.getDatabaseConfWorkout(type: WktDbType): IWorkoutRepository

