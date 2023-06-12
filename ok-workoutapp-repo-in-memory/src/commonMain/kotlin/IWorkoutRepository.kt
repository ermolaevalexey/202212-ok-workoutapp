package ru.otus.otuskotlin.workoutapp.repoInMemory
interface IWorkoutRepository {

  suspend fun createWorkout(req: DbWorkoutRequest): DbWorkoutResponse

  suspend fun readWorkout(req: DbWorkoutIdRequest): DbWorkoutResponse

  suspend fun updateWorkout(req: DbWorkoutRequest): DbWorkoutResponse

  suspend fun searchWorkouts(req: DbWorkoutSearchRequest): DbWorkoutsListResponse

  companion object {
    val NONE = object: IWorkoutRepository {
      override suspend fun createWorkout(req: DbWorkoutRequest): DbWorkoutResponse {
        TODO("Not yet implemented")
      }

      override suspend fun readWorkout(req: DbWorkoutIdRequest): DbWorkoutResponse {
        TODO("Not yet implemented")
      }

      override suspend fun updateWorkout(req: DbWorkoutRequest): DbWorkoutResponse {
        TODO("Not yet implemented")
      }

      override suspend fun searchWorkouts(req: DbWorkoutSearchRequest): DbWorkoutsListResponse {
        TODO("Not yet implemented")
      }
    }
  }
}