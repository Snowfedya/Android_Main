package com.willpower.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.willpower.tracker.storage.UserPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel for Settings screen with DataStore integration.
 * Wraps UserPreferencesManager and exposes preference Flows.
 * Manages backup/restore state and handles data clearing.
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = UserPreferencesManager(application)

    // Preference Flows from DataStore
    val notificationEnabled: kotlinx.coroutines.flow.Flow<Boolean> = preferencesManager.notificationEnabledFlow
    val reminderTime: kotlinx.coroutines.flow.Flow<Pair<Int, Int>> = preferencesManager.reminderTimeFlow
    val themeMode: kotlinx.coroutines.flow.Flow<String> = preferencesManager.themeModeFlow
    val userName: kotlinx.coroutines.flow.Flow<String> = preferencesManager.userNameFlow
    val userEmail: kotlinx.coroutines.flow.Flow<String> = preferencesManager.userEmailFlow

    // Backup/Restore State
    private val _backupStatus = MutableStateFlow<BackupStatus?>(null)
    val backupStatus: StateFlow<BackupStatus?> = _backupStatus.asStateFlow()

    private val _restoreStatus = MutableStateFlow<RestoreStatus?>(null)
    val restoreStatus: StateFlow<RestoreStatus?> = _restoreStatus.asStateFlow()

    private val _isClearingData = MutableStateFlow(false)
    val isClearingData: StateFlow<Boolean> = _isClearingData.asStateFlow()

    // Update methods for preferences
    fun setNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationEnabled(enabled)
        }
    }

    fun setReminderTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            preferencesManager.setReminderTime(hour, minute)
        }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            preferencesManager.setThemeMode(mode)
        }
    }

    fun setUserCredentials(name: String, email: String) {
        viewModelScope.launch {
            preferencesManager.saveUserCredentials(name, email)
        }
    }

    /**
     * Clear all user data from DataStore
     */
    fun clearAllData() {
        viewModelScope.launch {
            _isClearingData.value = true
            try {
                preferencesManager.clearAll()
                _backupStatus.value = BackupStatus.Success("Все данные очищены")
            } catch (e: Exception) {
                _backupStatus.value = BackupStatus.Error("Ошибка очистки: ${e.message}")
            } finally {
                _isClearingData.value = false
            }
        }
    }

    /**
     * Create backup data string for export
     */
    fun createBackupData(onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val userName = preferencesManager.userNameFlow.first()
                val userEmail = preferencesManager.userEmailFlow.first()
                val theme = preferencesManager.themeModeFlow.first()
                val (hour, minute) = preferencesManager.reminderTimeFlow.first()

                val backupData = """
                    Backup Date: ${java.util.Date()}
                    User: $userName ($userEmail)
                    Theme: $theme
                    Reminder Time: ${String.format("%02d:%02d", hour, minute)}

                """.trimIndent()

                _backupStatus.value = BackupStatus.Success("Бэкап успешно создан")
                onResult(backupData)
            } catch (e: Exception) {
                _backupStatus.value = BackupStatus.Error("Ошибка создания бэкапа: ${e.message}")
            }
        }
    }

    /**
     * Process restored backup data
     */
    fun processRestoreData(content: String) {
        viewModelScope.launch {
            try {
                // Simple parsing of backup data
                val lines = content.lines()
                var userName = ""
                var userEmail = ""
                for (line in lines) {
                    when {
                        line.contains("User:") && line.contains("(") -> {
                            userName = line.substringAfter("User: ").substringBefore(" (").trim()
                            userEmail = line.substringAfter("(").substringBefore(")").trim()
                        }
                        line.contains("Theme:") -> {
                            val theme = line.substringAfter("Theme: ").trim()
                            preferencesManager.setThemeMode(theme)
                        }
                        line.contains("Reminder Time:") -> {
                            val timePart = line.substringAfter("Reminder Time: ").trim()
                            val parts = timePart.split(":")
                            if (parts.size == 2) {
                                preferencesManager.setReminderTime(parts[0].toInt(), parts[1].toInt())
                            }
                        }
                    }
                }
                // Save user credentials after parsing
                if (userName.isNotEmpty() && userEmail.isNotEmpty()) {
                    preferencesManager.saveUserCredentials(userName, userEmail)
                }
                _restoreStatus.value = RestoreStatus.Success("Бэкап успешно восстановлен")
            } catch (e: Exception) {
                _restoreStatus.value = RestoreStatus.Error("Ошибка восстановления: ${e.message}")
            }
        }
    }

    /**
     * Clear status messages after displaying
     */
    fun clearBackupStatus() {
        _backupStatus.value = null
    }

    fun clearRestoreStatus() {
        _restoreStatus.value = null
    }

    // Sealed classes for backup/restore status
    sealed class BackupStatus {
        data class Success(val message: String) : BackupStatus()
        data class Error(val message: String) : BackupStatus()
    }

    sealed class RestoreStatus {
        data class Success(val message: String) : RestoreStatus()
        data class Error(val message: String) : RestoreStatus()
    }
}
