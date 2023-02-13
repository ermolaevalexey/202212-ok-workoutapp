package ru.otus.otuskotlin.workoutapp.m1l4.models

import java.time.LocalDateTime

enum class Action {
    CREATE,
    READ,
    UPDATE,
    DELETE
}

data class User(
    val id: String,
    val firstName: String,
    val secondName: String?,
    val lastName: String,

    val phone: String,
    val email: String,

    val actions: Set<Action>,

    val available: List<LocalDateTime>,
)