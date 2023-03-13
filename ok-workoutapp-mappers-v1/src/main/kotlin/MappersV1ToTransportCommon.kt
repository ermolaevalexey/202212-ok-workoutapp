import ru.otus.otuskotlin.workoutapp.api.v1.models.Error
import ru.otus.otuskotlin.workoutapp.common.models.WktError

fun List<WktError>.toTransportErrors(): List<Error>? = this
  .map { it.toTransport() }
  .toList()
  .takeIf { it.isNotEmpty() }

private fun WktError.toTransport() = Error(
  code = code.takeIf { it.isNotBlank() },
  group = group.takeIf { it.isNotBlank() },
  field = field.takeIf { it.isNotBlank() },
  message = message.takeIf { it.isNotBlank() },
)