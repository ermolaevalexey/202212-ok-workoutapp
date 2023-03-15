package ok.workoutapp.workout.app.ktor.plugins

import io.ktor.server.plugins.cachingheaders.*
import io.ktor.http.content.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import ru.otus.otuskotlin.workoutapp.api.v1.apiV1Mapper

fun Application.configureHTTP() {
  install(CachingHeaders) {
    options { _, outgoingContent ->
      when (outgoingContent.contentType?.withoutParameters()) {
        ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
        else -> null
      }
    }
  }
  install(DefaultHeaders) {
    header("X-Engine", "Ktor") // will send this header with each response
    header("Accept", "application/json")
  }
  install(CORS) {
    allowMethod(HttpMethod.Options)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Delete)
    allowMethod(HttpMethod.Patch)
    allowHeader(HttpHeaders.Authorization)
    allowHeader("MyCustomHeader")
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }
}