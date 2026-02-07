package com.willpower.tracker.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesManager(private val context: Context) {
    
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val REMINDER_HOUR = intPreferencesKey("reminder_hour")
        val REMINDER_MINUTE = intPreferencesKey("reminder_minute")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val TOTAL_COMPLETED = intPreferencesKey("total_completed")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
    }
    
    val userNameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }
    
    val userEmailFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL] ?: ""
    }
    
    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }
    
    val notificationEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATION_ENABLED] ?: true
    }
    
    val reminderTimeFlow: Flow<Pair<Int, Int>> = context.dataStore.data.map { preferences ->
        Pair(
            preferences[REMINDER_HOUR] ?: 9,
            preferences[REMINDER_MINUTE] ?: 0
        )
    }
    
    val themeModeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: "system"
    }
    
    val totalCompletedFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[TOTAL_COMPLETED] ?: 0
    }
    
    val currentStreakFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_STREAK] ?: 0
    }
    
    suspend fun saveUserCredentials(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
            preferences[IS_LOGGED_IN] = true
        }
    }
    
    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }
    
    suspend fun setNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED] = enabled
        }
    }
    
    suspend fun setReminderTime(hour: Int, minute: Int) {
        context.dataStore.edit { preferences ->
            preferences[REMINDER_HOUR] = hour
            preferences[REMINDER_MINUTE] = minute
        }
    }
    
    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }
    
    suspend fun incrementCompleted() {
        context.dataStore.edit { preferences ->
            val current = preferences[TOTAL_COMPLETED] ?: 0
            preferences[TOTAL_COMPLETED] = current + 1
        }
    }
    
    suspend fun updateStreak(streak: Int) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_STREAK] = streak
        }
    }
    
    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
        }
    }
    
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
