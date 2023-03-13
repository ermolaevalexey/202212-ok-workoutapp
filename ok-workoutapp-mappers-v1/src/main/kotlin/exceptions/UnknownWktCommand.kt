package ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions

import ru.otus.otuskotlin.workoutapp.common.models.WktCommand

class UnknownWktCommand(command: WktCommand) : Throwable("Wrong command $command at mapping toTransport stage")