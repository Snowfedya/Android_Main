package com.willpower.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.willpower.tracker.database.entities.CompletionEntity
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.database.dao.TaskDao
import com.willpower.tracker.database.dao.CompletionDao
import com.willpower.tracker.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Details screen with task loading and completion tracking.
 * Accepts taskId parameter via navigation arguments.
 *
 * Use DetailsViewModelFactory to create this ViewModel with taskId parameter.
 */
class DetailsViewModel(
    application: Application,
    private val taskId: Long
) : AndroidViewModel(application) {

    private val taskDao: TaskDao = AppDatabase.getDatabase(application).taskDao()
    private val completionDao: CompletionDao = AppDatabase.getDatabase(application).completionDao()

    // Task data as Flow for UI observation
    // Note: Using Flow directly because getTaskById is a suspend function
    // The Fragment will collect this Flow using lifecycle-aware collection
    val taskFlow: kotlinx.coroutines.flow.Flow<TaskEntity?> = kotlinx.coroutines.flow.flow {
        emit(taskDao.getTaskById(taskId.toInt()))
    }

    // Task data as StateFlow for UI observation (cached)
    private val _task = MutableStateFlow<TaskEntity?>(null)
    val task: StateFlow<TaskEntity?> = _task.asStateFlow()

    init {
        // Load task data on initialization
        viewModelScope.launch {
            _task.value = taskDao.getTaskById(taskId.toInt())
        }
    }

    // Focus mode timer state
    private val _timerRemaining = MutableStateFlow(0) // seconds remaining
    val timerRemaining: StateFlow<Int> = _timerRemaining.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    // UI State
    private val _isCompleting = MutableStateFlow(false)
    val isCompleting: StateFlow<Boolean> = _isCompleting.asStateFlow()

    private val _completionMessage = MutableStateFlow<String?>(null)
    val completionMessage: StateFlow<String?> = _completionMessage.asStateFlow()

    /**
     * Start focus mode countdown timer
     * @param durationMinutes Duration in minutes for the focus session
     */
    fun startTimer(durationMinutes: Int) {
        _timerRemaining.value = durationMinutes * 60
        _isTimerRunning.value = true

        viewModelScope.launch {
            while (_timerRemaining.value > 0 && _isTimerRunning.value) {
                kotlinx.coroutines.delay(1000)
                if (_isTimerRunning.value) {
                    _timerRemaining.value = _timerRemaining.value - 1
                }
            }

            if (_timerRemaining.value == 0) {
                // Timer completed naturally
                _isTimerRunning.value = false
                _completionMessage.value = "Фокус-режим завершен! Отличная работа!"
            }
        }
    }

    /**
     * Stop the focus mode timer
     */
    fun stopTimer() {
        _isTimerRunning.value = false
    }

    /**
     * Pause the focus mode timer
     */
    fun pauseTimer() {
        _isTimerRunning.value = false
    }

    /**
     * Resume the focus mode timer
     */
    fun resumeTimer() {
        if (_timerRemaining.value > 0) {
            _isTimerRunning.value = true
        }
    }

    /**
     * Mark task as completed by creating a CompletionEntity record
     */
    fun markComplete() {
        viewModelScope.launch {
            _isCompleting.value = true

            try {
                val currentTask = task.value
                if (currentTask != null) {
                    val durationSeconds = (currentTask.recommendedDurationMin * 60) - _timerRemaining.value
                    val durationMin = if (durationSeconds > 0) durationSeconds / 60 else currentTask.recommendedDurationMin

                    val completion = CompletionEntity(
                        taskId = currentTask.id,
                        dateTime = System.currentTimeMillis(),
                        durationMin = durationMin,
                        result = "completed",
                        note = if (_isTimerRunning.value) "Focus mode active" else "Manual completion"
                    )
                    completionDao.insert(completion)
                    _completionMessage.value = "Отлично! ${currentTask.title} выполнено!"
                } else {
                    _completionMessage.value = "Ошибка: задача не найдена"
                }
            } catch (e: Exception) {
                _completionMessage.value = "Ошибка сохранения: ${e.message}"
            } finally {
                _isCompleting.value = false
            }
        }
    }

    /**
     * Clear completion message after displaying
     */
    fun clearCompletionMessage() {
        _completionMessage.value = null
    }

    /**
     * Factory for creating DetailsViewModel with taskId parameter
     */
    class Factory(
        private val application: Application,
        private val taskId: Long
    ) : androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory(application) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                return DetailsViewModel(application, taskId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        /**
         * Create Factory from SavedStateHandle (for use with Navigation Component)
         */
        fun createFactory(savedStateHandle: SavedStateHandle, application: Application): Factory {
            val taskId = savedStateHandle.get<Long>("challengeId") ?: 0L
            return Factory(application, taskId)
        }
    }
}
