package com.willpower.tracker.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.database.ChallengeDao
import com.willpower.tracker.database.ChallengeEntity
import com.willpower.tracker.database.CompletionHistoryDao
import com.willpower.tracker.database.CompletionHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Calendar

class ChallengeRepository(private val database: AppDatabase) {
    
    private val TAG = "ChallengeRepository"
    private val challengeDao: ChallengeDao = database.challengeDao()
    private val historyDao: CompletionHistoryDao = database.completionHistoryDao()
    
    val allChallenges: Flow<List<ChallengeEntity>> = challengeDao.getAllChallenges()
    
    val completedChallenges: Flow<List<ChallengeEntity>> = 
        challengeDao.getChallengesByCompletionStatus(true)
    
    val pendingChallenges: Flow<List<ChallengeEntity>> = 
        challengeDao.getChallengesByCompletionStatus(false)
    
    val totalCount: Flow<Int> = challengeDao.getTotalCount()
    
    val completedCount: Flow<Int> = challengeDao.getCompletedCount()
    
    val totalCompletions: Flow<Int> = historyDao.getTotalCompletions()
    
    val allCategories: Flow<List<String>> = challengeDao.getAllCategories()
    
    fun getChallengesPaged(): Flow<PagingData<ChallengeEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = { challengeDao.getAllChallengesPaged() }
        ).flow
    }
    
    fun getChallengeById(id: Int): Flow<ChallengeEntity?> {
        return challengeDao.getChallengeById(id)
    }
    
    fun getChallengesByCategory(category: String): Flow<List<ChallengeEntity>> {
        return challengeDao.getChallengesByCategory(category)
    }
    
    fun searchChallenges(query: String): Flow<List<ChallengeEntity>> {
        return challengeDao.searchChallenges(query)
    }
    
    fun getHistoryForChallenge(challengeId: Int): Flow<List<CompletionHistoryEntity>> {
        return historyDao.getHistoryForChallenge(challengeId)
    }
    
    fun getHistoryPaged(): Flow<PagingData<CompletionHistoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { historyDao.getAllHistoryPaged() }
        ).flow
    }
    
    fun getTodayCompletions(): Flow<Int> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis
        
        return historyDao.getCompletionsForDay(startOfDay, endOfDay)
    }
    
    suspend fun addChallenge(challenge: ChallengeEntity): Long {
        Log.d(TAG, "Adding challenge: ${challenge.title}")
        return challengeDao.insert(challenge)
    }
    
    suspend fun updateChallenge(challenge: ChallengeEntity) {
        Log.d(TAG, "Updating challenge: ${challenge.title}")
        challengeDao.update(challenge)
    }
    
    suspend fun deleteChallenge(challenge: ChallengeEntity) {
        Log.d(TAG, "Deleting challenge: ${challenge.title}")
        challengeDao.delete(challenge)
    }
    
    suspend fun completeChallenge(challengeId: Int, notes: String? = null, durationActual: Int? = null) {
        Log.d(TAG, "Completing challenge: $challengeId")
        val now = System.currentTimeMillis()
        
        challengeDao.incrementStreakAndComplete(challengeId, now)
        
        historyDao.insert(
            CompletionHistoryEntity(
                challengeId = challengeId,
                completedDate = now,
                durationActual = durationActual,
                notes = notes
            )
        )
    }
    
    suspend fun uncompleteChallenge(challengeId: Int) {
        Log.d(TAG, "Uncompleting challenge: $challengeId")
        challengeDao.updateCompletionStatus(challengeId, isCompleted = false, completedAt = null)
    }
    
    suspend fun resetDailyProgress() {
        Log.d(TAG, "Resetting daily progress")
        challengeDao.resetAllCompletionStatus()
    }
    
    suspend fun checkAndResetStreaks() {
        Log.d(TAG, "Checking and resetting streaks")
        val challenges = allChallenges.first()
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis
        
        challenges.forEach { challenge ->
            val lastCompleted = challenge.lastCompletedAt
            if (lastCompleted != null && lastCompleted < yesterday && challenge.streak > 0) {
                Log.d(TAG, "Resetting streak for challenge: ${challenge.id}")
                challengeDao.resetStreak(challenge.id)
            }
        }
    }
    
    suspend fun coldStart(): Boolean {
        Log.d(TAG, "Performing cold start")
        try {
            checkAndResetStreaks()
            
            val count = totalCount.first()
            Log.d(TAG, "Cold start complete. Total challenges: $count")
            
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Cold start failed", e)
            return false
        }
    }
}
