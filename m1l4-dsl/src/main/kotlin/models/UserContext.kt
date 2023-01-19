package ru.otus.otuskotlin.workoutapp.m1l4.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import java.util.UUID

class NameContext {
    var first: String = ""
    var second: String = ""
    var last: String = ""
}

class ContactsDsl {
    var email: String = ""
    var phone: String = ""
}

class ActionsContext {
    private val _actions: MutableSet<Action> = mutableSetOf()
    val actions: Set<Action>
        get() = _actions.toSet()

    fun add(action: Action) {
        _actions.add(action)
    }

    operator fun Action.unaryPlus() {
        add(this)
    }
}

class AvailabilityContext {
    private val _availabilities: MutableList<LocalDateTime> = mutableListOf()
    val availabilities: List<LocalDateTime>
        get() = _availabilities.toList()

    fun monday(time: String) = dayTimeOfWeek(DayOfWeek.MONDAY, time)
    fun tuesday(time: String) = dayTimeOfWeek(DayOfWeek.TUESDAY, time)
    fun wednesday(time: String) = dayTimeOfWeek(DayOfWeek.WEDNESDAY, time)
    fun thursday(time: String) = dayTimeOfWeek(DayOfWeek.THURSDAY, time)
    fun friday(time: String) = dayTimeOfWeek(DayOfWeek.FRIDAY, time)
    fun saturday(time: String) = dayTimeOfWeek(DayOfWeek.SATURDAY, time)
    fun sunday(time: String) = dayTimeOfWeek(DayOfWeek.SUNDAY, time)

    private fun dayTimeOfWeek(dayOfWeek: DayOfWeek, time: String) {
        val day = LocalDate.now().with(TemporalAdjusters.next(dayOfWeek))
        val time = time.split(":")
            .map { it.toInt() }
            .let { LocalTime.of(it[0], it[1]) }

        _availabilities.add(LocalDateTime.of(day, time))
    }
}

@UserDsl
class UserContext {
    private val id = UUID.randomUUID().toString()

    private var firstName: String = ""
    private var secondName: String = ""
    private var lastName: String = ""

    private var contacts: ContactsDsl = ContactsDsl()

    private var actions: Set<Action> = emptySet()

    private var available: List<LocalDateTime> = emptyList()

    @UserDsl
    fun name(block: NameContext.() -> Unit) {
        val ctx = NameContext()
        ctx.block()

        firstName = ctx.first
        secondName = ctx.second
        lastName = ctx.last
    }

    @UserDsl
    fun contacts(block: ContactsDsl.() -> Unit) {
        val ctx = ContactsDsl()
        ctx.block()

        contacts.email = ctx.email
        contacts.phone = ctx.phone
    }

    @UserDsl
    fun actions(block: ActionsContext.() -> Unit) {
        val ctx = ActionsContext()
        ctx.block()

        actions = ctx.actions
    }

    @UserDsl
    fun availablility(block: AvailabilityContext.() -> Unit) {
        val ctx = AvailabilityContext()
        ctx.block()

        available = ctx.availabilities
    }

    @UserDsl
    fun build() = User(
        id,
        firstName,
        secondName,
        lastName,
        contacts.phone,
        contacts.email,
        actions,
        available
    )
}

// Можно сократить
//fun buildUser(block: UserDsl.() -> Unit) {
//    val ctx = UserDsl()
//    ctx.block()
//
//    return ctx.build()
//}


// Можно сократить
fun buildUser(block: UserContext.() -> Unit) = UserContext().apply(block).build()

@DslMarker
annotation class UserDsl