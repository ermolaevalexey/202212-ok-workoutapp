package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.workoutapp.repoInMemory.FeedbackRepoInMemory
import ru.otus.otuskotlin.workoutapp.repoInMemory.IFeedbackRepository
import ru.otus.otuskotlin.workoutapp.repoInMemory.IWorkoutRepository
import ru.otus.otuskotlin.workoutapp.repoInMemory.WorkoutRepoInMemory
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConfWorkout(): IWorkoutRepository {
    return initInMemoryWorkout()
}

fun Application.getDatabaseConfFeedback(wktRepo: WorkoutRepoInMemory): IFeedbackRepository {
    return initInMemoryFeedback(wktRepo)
}

private fun Application.initInMemoryWorkout(): WorkoutRepoInMemory {
    return WorkoutRepoInMemory(ttl = 10.minutes)
}

private fun Application.initInMemoryFeedback(wktRepo: WorkoutRepoInMemory): FeedbackRepoInMemory {
    return FeedbackRepoInMemory(ttl = 10.minutes, wktRepo = wktRepo)
}