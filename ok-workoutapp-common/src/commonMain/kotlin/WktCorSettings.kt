import ru.otus.otuskotlin.workoutapp.repoInMemory.IFeedbackRepository
import ru.otus.otuskotlin.workoutapp.repoInMemory.IWorkoutRepository

data class WktCorSettings(
  val workoutRepoStub: IWorkoutRepository = IWorkoutRepository.NONE,
  val workoutRepoTest: IWorkoutRepository = IWorkoutRepository.NONE,
  val workoutRepoProd: IWorkoutRepository = IWorkoutRepository.NONE,
  val feedbackRepoStub: IFeedbackRepository = IFeedbackRepository.NONE,
  val feedbackRepoTest: IFeedbackRepository = IFeedbackRepository.NONE,
  val feedbackRepoProd: IFeedbackRepository = IFeedbackRepository.NONE
) {
  companion object {
    val NONE = WktCorSettings()
  }
}
