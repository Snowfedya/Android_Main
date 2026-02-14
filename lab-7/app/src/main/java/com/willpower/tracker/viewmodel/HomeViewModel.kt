package com.willpower.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.willpower.tracker.database.entities.AiAdviceEntity
import com.willpower.tracker.database.entities.TaskEntity
import com.willpower.tracker.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Home screen with Flow-based task and advice data.
 * Exposes reactive streams from Room database and manages UI state.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ChallengeRepository(application)

    // Flow-based reactive data from Room database
    val tasks: kotlinx.coroutines.flow.Flow<List<TaskEntity>> = repository.getAllTasks()
    val latestAdvice: kotlinx.coroutines.flow.Flow<AiAdviceEntity?> = repository.getLatestAdvice()

    // UI State
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * Refresh advice from AI API
     */
    fun refreshAdvice() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.refreshAdvice()
                .onFailure { exception ->
                    _errorMessage.value = "Ошибка загрузки совета: ${exception.message}"
                }
                .onSuccess {
                    // Advice automatically saved to database, Flow will update UI
                }

            _isLoading.value = false
        }
    }

    /**
     * Clear error message after displaying to user
     */
    fun clearError() {
        _errorMessage.value = null
    }
}
