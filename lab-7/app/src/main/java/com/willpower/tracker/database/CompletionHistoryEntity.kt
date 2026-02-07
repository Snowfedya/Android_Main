package com.willpower.tracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "completion_history",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challenge_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["challenge_id"]), Index(value = ["completed_date"])]
)
data class CompletionHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "challenge_id")
    val challengeId: Int,
    
    @ColumnInfo(name = "completed_date")
    val completedDate: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "duration_actual")
    val durationActual: Int? = null,
    
    @ColumnInfo(name = "notes")
    val notes: String? = null
)
