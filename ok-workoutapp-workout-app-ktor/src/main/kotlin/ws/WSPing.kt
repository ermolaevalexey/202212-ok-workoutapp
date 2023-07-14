package ru.otus.otuskotlin.workoutapp.workout.app.ktor.ws

import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow

suspend fun WebSocketSession.wsPing() {
  send("Please enter your name")

  incoming.receiveAsFlow().mapNotNull {
    if (it !is Frame.Text) {
      return@mapNotNull
    }
    send(Frame.Text("Hi, ${it.readText()}"))
  }.collect()
}