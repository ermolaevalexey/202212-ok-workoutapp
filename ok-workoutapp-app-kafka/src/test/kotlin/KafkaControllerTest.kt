package ru.otus.otuskotlin.marketplace.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.app.kafka.AppKafkaConfig
import ru.otus.otuskotlin.workoutapp.app.kafka.AppKafkaConsumer
import ru.otus.otuskotlin.workoutapp.app.kafka.ConsumerStrategyWorkout
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {
  @Test
  fun runKafka() {
    val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
    val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
    val config = AppKafkaConfig()
    val inputTopic = config.kafkaTopicInWorkout
    val outputTopic = config.kafkaTopicOutWorkout

    val app = AppKafkaConsumer(config, listOf(ConsumerStrategyWorkout()), consumer = consumer, producer = producer)
    consumer.schedulePollTask {
      consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
      consumer.addRecord(
        ConsumerRecord(
          inputTopic,
          PARTITION,
          0L,
          "test-1",
          apiV1RequestSerialize(WorkoutCreateRequest(
            requestId = "11111111-1111-1111-1111-111111111111",
            workout = WorkoutBase(
              title = "Сильные руки на брусьях",
              description = "some testing ad to check them all",
              equipment = Equipment.BARS,
              type = WorkoutType.ARMS
            ),
            debug = Debug(
              mode = RequestDebugMode.STUB,
              stub = RequestDebugStubs.SUCCESS
            )
          ))
        )
      )
      app.stop()
    }

    val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
    val tp = TopicPartition(inputTopic, PARTITION)
    startOffsets[tp] = 0L
    consumer.updateBeginningOffsets(startOffsets)

    app.run()

    val message = producer.history().first()
    val result = apiV1ResponseDeserialize<WorkoutCreateResponse>(message.value())
    assertEquals(outputTopic, message.topic())
    assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
    assertEquals("Сильные руки на брусьях", result.workout?.title)
  }

  companion object {
    const val PARTITION = 0
  }
}

