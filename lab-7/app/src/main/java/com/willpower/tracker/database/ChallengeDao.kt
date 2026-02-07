package com.willpower.tracker.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    
    @Query("SELECT * FROM challenges ORDER BY created_at DESC")
    fun getAllChallenges(): Flow<List<ChallengeEntity>>
    
    @Query("SELECT * FROM challenges ORDER BY created_at DESC")
    fun getAllChallengesPaged(): PagingSource<Int, ChallengeEntity>
    
    @Query("SELECT * FROM challenges WHERE id = :id")
    fun getChallengeById(id: Int): Flow<ChallengeEntity?>
    
    @Query("SELECT * FROM challenges WHERE id = :id")
    suspend fun getChallengeByIdOnce(id: Int): ChallengeEntity?
    
    @Query("SELECT * FROM challenges WHERE category = :category ORDER BY created_at DESC")
    fun getChallengesByCategory(category: String): Flow<List<ChallengeEntity>>
    
    @Query("SELECT * FROM challenges WHERE is_completed = :isCompleted ORDER BY created_at DESC")
    fun getChallengesByCompletionStatus(isCompleted: Boolean): Flow<List<ChallengeEntity>>
    
    @Query("SELECT * FROM challenges WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchChallenges(query: String): Flow<List<ChallengeEntity>>
    
    @Query("SELECT COUNT(*) FROM challenges")
    fun getTotalCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM challenges WHERE is_completed = 1")
    fun getCompletedCount(): Flow<Int>
    
    @Query("SELECT SUM(streak) FROM challenges")
    fun getTotalStreak(): Flow<Int?>
    
    @Query("SELECT DISTINCT category FROM challenges ORDER BY category")
    fun getAllCategories(): Flow<List<String>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: ChallengeEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(challenges: List<ChallengeEntity>)
    
    @Update
    suspend fun update(challenge: ChallengeEntity)
    
    @Delete
    suspend fun delete(challenge: ChallengeEntity)
    
    @Query("DELETE FROM challenges WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM challenges")
    suspend fun deleteAll()
    
    @Query("UPDATE challenges SET is_completed = :isCompleted, last_completed_at = :completedAt WHERE id = :id")
    suspend fun updateCompletionStatus(id: Int, isCompleted: Boolean, completedAt: Long?)
    
    @Query("UPDATE challenges SET streak = streak + 1, is_completed = 1, last_completed_at = :completedAt WHERE id = :id")
    suspend fun incrementStreakAndComplete(id: Int, completedAt: Long = System.currentTimeMillis())
    
    @Query("UPDATE challenges SET streak = 0, is_completed = 0 WHERE id = :id")
    suspend fun resetStreak(id: Int)
    
    @Query("UPDATE challenges SET is_completed = 0 WHERE is_completed = 1")
    suspend fun resetAllCompletionStatus()
}
