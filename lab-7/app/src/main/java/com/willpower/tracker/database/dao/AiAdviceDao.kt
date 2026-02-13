package com.willpower.tracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.willpower.tracker.database.entities.AiAdviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiAdviceDao {
    @Query("SELECT * FROM ai_advice ORDER BY dateTime DESC LIMIT 1")
    fun getLatestAdvice(): Flow<AiAdviceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(advice: AiAdviceEntity)

    @Query("SELECT * FROM ai_advice ORDER BY dateTime DESC")
    fun getAllAdvices(): Flow<List<AiAdviceEntity>>

    @Query("DELETE FROM ai_advice")
    suspend fun clearAll()
}
