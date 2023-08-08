package ru.otus.otuskotlin.workoutapp.feedback.common.repo

interface IFeedbackRepository {
  suspend fun createFeedback(req: DbFeedbackCreateRequest): DbFeedbackResponse

  suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse

  suspend fun updateFeedback(req: DbFeedbackUpdateRequest): DbFeedbackResponse

  suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse

  companion object {
    val NONE = object: IFeedbackRepository {
      override suspend fun createFeedback(req: DbFeedbackCreateRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }

      override suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse {
        TODO("Not yet implemented")
      }

      override suspend fun updateFeedback(req: DbFeedbackUpdateRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }

      override suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse {
        TODO("Not yet implemented")
      }
    }
  }
}