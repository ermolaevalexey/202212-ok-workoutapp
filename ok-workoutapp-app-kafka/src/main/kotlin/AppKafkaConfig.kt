package ru.otus.otuskotlin.workoutapp.app.kafka

class AppKafkaConfig(
  val kafkaHosts: List<String> = KAFKA_HOSTS,
  val kafkaGroupId: String = KAFKA_GROUP_ID,
  val kafkaTopicInWorkout: String = KAFKA_TOPIC_IN_WORKOUT,
  val kafkaTopicOutWorkout: String = KAFKA_TOPIC_OUT_WORKOUT,
  val kafkaTopicInFeedback: String = KAFKA_TOPIC_IN_FEEDBACK,
  val kafkaTopicOutFeedback: String = KAFKA_TOPIC_OUT_FEEDBACK
) {
  companion object {
    const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
    const val KAFKA_TOPIC_IN_WORKOUT_VAR = "KAFKA_TOPIC_IN_WORKOUT"
    const val KAFKA_TOPIC_OUT_WORKOUT_VAR = "KAFKA_TOPIC_OUT_WORKOUT"
    const val KAFKA_TOPIC_IN_FEEDBACK_VAR = "KAFKA_TOPIC_IN_FEEDBACK"
    const val KAFKA_TOPIC_OUT_FEEDBACK_VAR = "KAFKA_TOPIC_OUT_FEEDBACK"
    const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

    val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
    val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "workoutapp" }
    val KAFKA_TOPIC_IN_WORKOUT by lazy { System.getenv(KAFKA_TOPIC_IN_WORKOUT_VAR) ?: "workoutapp-in-workout" }
    val KAFKA_TOPIC_OUT_WORKOUT by lazy { System.getenv(KAFKA_TOPIC_OUT_WORKOUT_VAR) ?: "workoutapp-out-workout" }
    val KAFKA_TOPIC_IN_FEEDBACK by lazy { System.getenv(KAFKA_TOPIC_IN_FEEDBACK_VAR) ?: "workoutapp-in-feedback" }
    val KAFKA_TOPIC_OUT_FEEDBACK by lazy { System.getenv(KAFKA_TOPIC_OUT_FEEDBACK_VAR) ?: "workoutapp-out-feedback"}
  }
}