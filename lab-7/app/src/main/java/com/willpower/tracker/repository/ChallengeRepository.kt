package com.willpower.tracker.repository

import android.content.Context
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.database.dao.AiAdviceDao
import com.willpower.tracker.database.dao.TaskDao
import com.willpower.tracker.database.entities.AiAdviceEntity
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.models.Challenge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChallengeRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val taskDao: TaskDao = db.taskDao()
    private val adviceDao: AiAdviceDao = db.aiAdviceDao()
    private val adviceRepo = AdviceRepository(context)

    init {
        // Pre-populate with sample challenges if empty
        CoroutineScope(Dispatchers.IO).launch {
            if (taskDao.getAllTasks().first().isEmpty()) {
                val sampleTasks = Challenge.getSampleChallenges().map { challenge ->
                    TaskEntity(
                        title = challenge.title,
                        techniqueName = challenge.technique,
                        description = challenge.description,
                        recommendedDurationMin = challenge.durationMinutes,
                        difficulty = challenge.difficulty,
                        category = challenge.category
                    )
                }
                taskDao.insertAll(sampleTasks)
            }
        }
    }

    fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    fun getLatestAdvice(): Flow<AiAdviceEntity?> = adviceDao.getLatestAdvice()

    suspend fun refreshAdvice(): Result<AiAdviceEntity> {
        val result = adviceRepo.getRandomAdvice()
        return result.fold(
            onSuccess = { advice ->
                val entity = AiAdviceEntity(
                    id = advice.id,
                    text = advice.text,
                    tags = advice.tags.joinToString(","),
                    mood = advice.mood,
                    source = advice.source,
                    dateTime = advice.dateTime
                )
                adviceDao.insert(entity)
                Result.success(entity)
            },
            onFailure = { e -> Result.failure(e) }
        )
    }

    suspend fun getTaskById(taskId: Int): TaskEntity? = taskDao.getTaskById(taskId)
}
