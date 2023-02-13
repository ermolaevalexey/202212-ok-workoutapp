package ru.otus.otuskotlin.workoutapp.m1l4

import junit.framework.TestCase.assertTrue
import ru.otus.otuskotlin.workoutapp.m1l4.models.Action
import ru.otus.otuskotlin.workoutapp.m1l4.models.buildUser
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDslTest {
    @Test
    fun `test user`() {
        val user = buildUser {
            name {
                first = "Aleksey"
                last = "Ermolaev"
                second = "Ivanovich"
            }
            contacts {
                email = "aermolaev@kotlindslrules.com"
                phone = "81234567890"
            }
            actions {
                add(Action.UPDATE)
                add(Action.CREATE)

                +Action.DELETE
                +Action.READ
            }

            availablility {
                monday("11:30")
                friday("18:00")
            }

        }

        assertTrue(user.id.isNotEmpty())
        assertEquals("Aleksey", user.firstName)
        assertEquals("Ivanovich", user.secondName)
        assertEquals("Ermolaev", user.lastName)
        assertEquals("aermolaev@kotlindslrules.com", user.email)
        assertEquals("81234567890", user.phone)
        assertEquals(setOf(Action.UPDATE, Action.CREATE, Action.DELETE, Action.READ), user.actions)
        assertEquals(2, user.available.size)
    }
}