package ru.otus.otuskotlin.workoutapp.repo.postgres

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserRole
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser

object UserTable : Table("users") {
  val id = varchar("id", 128).uniqueIndex()
  val name = varchar("name", 50)
  val role = varchar("role", 50)

  fun from(res : ResultRow) = WktFeedbackUser(
    id = WktUserId(res[id]),
    name = res[name],
    role = WktUserRole.fromValue(res[role].toString())
  )

  fun to(it: UpdateBuilder<*>, user: WktFeedbackUser, randomUuid: () -> String) {
    it[id] = user.id.takeIf { it != WktUserId.NONE }?.asString() ?: randomUuid()
    it[name] = user.name
    it[role] = WktUserRole.toValue(user.role)
  }
}