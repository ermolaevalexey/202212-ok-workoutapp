package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.IFeedbackRepository
import ru.otus.otuskotlin.workoutapp.repoInMemory.FeedbackRepoInMemory
import ru.otus.otuskotlin.workoutapp.repoInMemory.WorkoutRepoInMemory
import ru.otus.otuskotlin.workoutapp.workout.common.repo.IWorkoutRepository
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConfWorkout(): WorkoutRepoInMemory {
    return initInMemoryWorkout()
}

fun Application.getDatabaseConfFeedback(wktRepo: WorkoutRepoInMemory): FeedbackRepoInMemory {
    return initInMemoryFeedback(wktRepo)
}

private fun Application.initInMemoryWorkout(): WorkoutRepoInMemory {
    return WorkoutRepoInMemory(ttl = 10.minutes)
}

private fun Application.initInMemoryFeedback(wktRepo: WorkoutRepoInMemory): FeedbackRepoInMemory {
    return FeedbackRepoInMemory(ttl = 10.minutes, wktRepo = wktRepo)
}