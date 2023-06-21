package ru.otus.otuskotlin.workoutapp.repoInMemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.repoInMemory.WorkoutRepoInMemory.Companion.resultErrorNotFound
import ru.otus.otuskotlin.workoutapp.repoInMemory.models.FeedbackEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class FeedbackRepoInMemory(
    initObjects: List<WktFeedback> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
    val wktRepo: WorkoutRepoInMemory
): IFeedbackRepository {
    private val cache = Cache.Builder<String, MutableList<FeedbackEntity>>()
        .expireAfterWrite(ttl)
        .build()

    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(fb: WktFeedback) {
        val entity = FeedbackEntity(fb)
        val fbs = cache.get(entity.workout.asString())?.toMutableList() ?: mutableListOf()
        fbs.add(entity)
        cache.put(entity.workout.asString(), fbs)
    }

    override suspend fun createFeedback(req: DbFeedbackRequest): DbFeedbackResponse {
        val wktKey = req.workoutId.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyWorkoutId
        val wktResp = wktRepo.readWorkout(DbWorkoutIdRequest(WktWorkoutId(wktKey)));
        if (wktResp.isSuccess) {
            val key = randomUuid()
            val fb = WktFeedback(
                id = WktFeedbackId(key),
                workout = WktWorkoutId(wktKey),
                review = req.data.review,
                rating = req.data.rating
            )
            val entity = FeedbackEntity(fb)
            val fbs = cache.get(entity.workout.asString())?.toMutableList() ?: mutableListOf()
            fbs.add(entity)
            cache.put(req.workoutId.asString(), fbs)

            return DbFeedbackResponse(
                data = fb,
                isSuccess = true
            )
        }

        return resultErrorWktNotFound
    }

    override suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse {
        val key = req.workoutId.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyListId
        val wktResp = wktRepo.readWorkout(DbWorkoutIdRequest(WktWorkoutId(key)));
        if (wktResp.isSuccess) {
            return DbFeedbackListResponse(
                data = cache.get(key)?.toList()?.map { it.toInternal() } ?: listOf(),
                isSuccess = true
            )
        }
        return resultErrorListNotFound
    }

    override suspend fun updateFeedback(req: DbFeedbackRequest): DbFeedbackResponse {
        val key = req.workoutId.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyWorkoutId
        val wktResp = wktRepo.readWorkout(DbWorkoutIdRequest(WktWorkoutId(key)));
        if (wktResp.isSuccess) {
            return mutex.withLock {
                val oldFbs = cache.get(key)?.toMutableList<FeedbackEntity>()
                val oldFb = oldFbs?.find {
                    it.id == req.data.id
                }
                when (oldFb) {
                  null -> resultErrorFbNotFound
                  else -> {
                      val newFb = req.data.copy()
                      val newFbs = oldFbs.mapIndexed { index, feedbackEntity ->
                          if (feedbackEntity.id == newFb.id) newFb else feedbackEntity
                      }
                      cache.put(key, newFbs as MutableList<FeedbackEntity>)
                      DbFeedbackResponse(
                          data = newFbs.get(newFbs.indexOf(newFb)).toInternal(),
                          isSuccess = true
                      )
                  }
                }
            }
        }
        return resultErrorWktNotFound
    }

    override suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse {
        val key = req.workoutId.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyWorkoutId
        val wktResp = wktRepo.readWorkout(DbWorkoutIdRequest(WktWorkoutId(key)))

        if (wktResp.isSuccess) {
            return mutex.withLock {
                val oldFbs = cache.get(key)?.toMutableList<FeedbackEntity>()
                val oldFb = oldFbs?.find {
                    it.id == req.feedbackId
                }

                when (oldFb) {
                    null -> resultErrorFbNotFound
                    else -> {
                        val newFbs = oldFbs?.filterIndexed { _, feedbackEntity ->
                            feedbackEntity.id.asString() !== req.feedbackId.asString()
                        }

                        if (newFbs != null) {
                            cache.put(key, newFbs.toMutableList())
                        }

                        return DbFeedbackResponse(
                            data = oldFb?.toInternal(),
                            isSuccess = true
                        )
                    }
                }
            }
        }

        return resultErrorWktNotFound
    }

    companion object {
        val resultErrorEmptyListId = DbFeedbackListResponse(
            data = emptyList(),
            isSuccess = false,
            errors = listOf(
                WktError(
                    field = "workout",
                    message = "workout id must not be null or blank"
                )
            )
        )

        val resultErrorFbNotFound = DbFeedbackResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                WktError(
                    field = "id",
                    message = "feedback with such id not found"
                )
            )
        )

        val resultErrorEmptyWorkoutId = DbFeedbackResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                WktError(
                    field = "workout",
                    message = "workout id must not be null or blank"
                )
            )
        )

        val resultErrorWktNotFound = DbFeedbackResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                WktError(
                    field = "workout",
                    message = "workout with such id not found"
                )
            )
        )

        val resultErrorListNotFound = DbFeedbackListResponse(
            data = emptyList(),
            isSuccess = false,
            errors = listOf(
                WktError(
                    field = "workout",
                    message = "workout with such id not found"
                )
            )
        )
    }
}