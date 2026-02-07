package com.willpower.tracker.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionHistoryDao {
    
    @Query("SELECT * FROM completion_history ORDER BY completed_date DESC")
    fun getAllHistory(): Flow<List<CompletionHistoryEntity>>
    
    @Query("SELECT * FROM completion_history ORDER BY completed_date DESC")
    fun getAllHistoryPaged(): PagingSource<Int, CompletionHistoryEntity>
    
    @Query("SELECT * FROM completion_history WHERE challenge_id = :challengeId ORDER BY completed_date DESC")
    fun getHistoryForChallenge(challengeId: Int): Flow<List<CompletionHistoryEntity>>
    
    @Query("SELECT * FROM completion_history WHERE completed_date BETWEEN :startDate AND :endDate ORDER BY completed_date DESC")
    fun getHistoryBetweenDates(startDate: Long, endDate: Long): Flow<List<CompletionHistoryEntity>>
    
    @Query("SELECT COUNT(*) FROM completion_history WHERE challenge_id = :challengeId")
    fun getCompletionCountForChallenge(challengeId: Int): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM completion_history WHERE completed_date BETWEEN :startOfDay AND :endOfDay")
    fun getCompletionsForDay(startOfDay: Long, endOfDay: Long): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM completion_history")
    fun getTotalCompletions(): Flow<Int>
    
    @Insert
    suspend fun insert(history: CompletionHistoryEntity): Long
    
    @Query("DELETE FROM completion_history WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM completion_history WHERE challenge_id = :challengeId")
    suspend fun deleteAllForChallenge(challengeId: Int)
    
    @Query("DELETE FROM completion_history")
    suspend fun deleteAll()
}
