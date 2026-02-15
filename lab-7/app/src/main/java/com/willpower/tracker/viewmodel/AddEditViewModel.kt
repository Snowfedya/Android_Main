package com.willpower.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.database.entities.TaskEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    application: Application,
    private val taskId: Long
) : AndroidViewModel(application) {

    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    
    private val _task = MutableStateFlow<TaskEntity?>(null)
    val task: StateFlow<TaskEntity?> = _task.asStateFlow()
    
    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    init {
        if (taskId != -1L) {
            viewModelScope.launch {
                _task.value = taskDao.getTaskById(taskId.toInt())
            }
        }
    }
    
    fun saveTask(
        title: String,
        description: String,
        technique: String,
        duration: Int,
        difficulty: String,
        category: String
    ) {
        viewModelScope.launch {
            val currentTask = _task.value
            val newTask = if (currentTask != null) {
                currentTask.copy(
                    title = title,
                    description = description,
                    techniqueName = technique,
                    recommendedDurationMin = duration,
                    difficulty = difficulty,
                    category = category
                )
            } else {
                TaskEntity(
                    title = title,
                    description = description,
                    techniqueName = technique,
                    recommendedDurationMin = duration,
                    difficulty = difficulty,
                    category = category,
                    isCustom = true
                )
            }
            
            taskDao.insert(newTask)
            _saveResult.emit(true)
        }
    }

    class Factory(
        private val application: Application,
        private val taskId: Long
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddEditViewModel(application, taskId) as T
        }
    }
}
