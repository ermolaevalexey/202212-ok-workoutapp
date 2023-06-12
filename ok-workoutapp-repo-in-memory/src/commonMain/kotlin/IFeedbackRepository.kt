package ru.otus.otuskotlin.workoutapp.repoInMemory

interface IFeedbackRepository {
  suspend fun createFeedback(req: DbFeedbackRequest): DbFeedbackResponse

  suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse

  suspend fun updateFeedback(req: DbFeedbackRequest): DbFeedbackResponse

  suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse

  companion object {
    val NONE = object: IFeedbackRepository {
      override suspend fun createFeedback(req: DbFeedbackRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }

      override suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse {
        TODO("Not yet implemented")
      }

      override suspend fun updateFeedback(req: DbFeedbackRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }

      override suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }
    }
  }
}