package ru.otus.otuskotlin.workoutapp.api.v1

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import ru.otus.otuskotlin.workoutapp.api.v1.models.Request
import ru.otus.otuskotlin.workoutapp.api.v1.models.Response

val apiV1Mapper: ObjectMapper = JsonMapper.builder()
  .configure(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL, true)
  .serializationInclusion(JsonInclude.Include.NON_NULL)
  .build()

fun apiV1RequestSerialize(request: Request): String = apiV1Mapper.writeValueAsString(request);

@Suppress("UNCHECKED_CAST")
fun <T: Request> apiV1RequestDeserialize(json: String): T =
  apiV1Mapper.readValue(json, Request::class.java) as T

fun apiV1ResponseSerialize(response: Response): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T: Response> apiV1ResponseDeserialize(json: String): T =
  apiV1Mapper.readValue(json, Response::class.java) as T