package com.willpower.tracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.willpower.tracker.database.entities.CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {
    @Query("SELECT * FROM completions ORDER BY dateTime DESC")
    fun getAllCompletions(): Flow<List<CompletionEntity>>

    @Query("SELECT * FROM completions WHERE taskId = :taskId")
    fun getCompletionsForTask(taskId: Int): Flow<List<CompletionEntity>>

    @Insert
    suspend fun insert(completion: CompletionEntity): Long

    @Query("SELECT SUM(durationMin) FROM completions WHERE dateTime >= :startDate")
    suspend fun getTotalFocusTime(startDate: Long): Int?

    @Query("DELETE FROM completions")
    suspend fun clearAll()
}
