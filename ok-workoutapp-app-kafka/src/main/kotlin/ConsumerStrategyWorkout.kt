package ru.otus.otuskotlin.workoutapp.app.kafka

import ru.otus.otuskotlin.workoutapp.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.workoutapp.api.v1.models.Request
import ru.otus.otuskotlin.workoutapp.api.v1.models.Response
import ru.otus.otuskotlin.workoutapp.common.models.WktContext
import ru.otus.otuskotlin.workoutapp.mappers.v1.fromTransport
import ru.otus.otuskotlin.workoutapp.mappers.v1.toTransport
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

class ConsumerStrategyWorkout: ConsumerStrategy<WktWorkoutContext> {
  override fun topics(config: AppKafkaConfig): InputOutputTopics {
    return InputOutputTopics(config.kafkaTopicInWorkout, config.kafkaTopicOutWorkout)
  }

  override fun serialize(source: WktContext): String {
    val response: Response = (source as WktWorkoutContext).toTransport()
    return apiV1ResponseSerialize(response)
  }

  override fun deserialize(value: String, target: WktContext) {
    val request: Request = apiV1RequestDeserialize(value)
    (target as WktWorkoutContext).fromTransport(request)
  }
}