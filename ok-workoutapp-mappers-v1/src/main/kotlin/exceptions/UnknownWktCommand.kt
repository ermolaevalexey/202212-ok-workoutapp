package ru.otus.otuskotlin.marketplace.mappers.v1.exceptions

import models.WktCommand

class UnknownWktCommand(command: WktCommand) : Throwable("Wrong command $command at mapping toTransport stage")