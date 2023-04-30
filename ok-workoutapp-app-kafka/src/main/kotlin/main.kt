package ru.otus.otuskotlin.workoutapp.app.kafka

fun main() {
  val config = AppKafkaConfig()
  val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyWorkout(), ConsumerStrategyFeedback()))
  consumer.run()
}
