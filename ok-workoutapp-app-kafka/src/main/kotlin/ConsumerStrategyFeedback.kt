package ru.otus.otuskotlin.workoutapp.app.kafka

import WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.workoutapp.api.v1.models.Request
import ru.otus.otuskotlin.workoutapp.api.v1.models.Response
import ru.otus.otuskotlin.workoutapp.common.models.WktContext
import ru.otus.otuskotlin.workoutapp.mappers.v1.fromTransport
import ru.otus.otuskotlin.workoutapp.mappers.v1.toTransport

class ConsumerStrategyFeedback: ConsumerStrategy<WktFeedbackContext> {
  override fun topics(config: AppKafkaConfig): InputOutputTopics {
    return InputOutputTopics(config.kafkaTopicInFeedback, config.kafkaTopicOutFeedback)
  }

  override fun serialize(source: WktContext): String {
    val response: Response = (source as WktFeedbackContext).toTransport()
    return apiV1ResponseSerialize(response)
  }

  override fun deserialize(value: String, target: WktContext) {
    val request: Request = apiV1RequestDeserialize(value)
    (target as WktFeedbackContext).fromTransport(request)
  }
}