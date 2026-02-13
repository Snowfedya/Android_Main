package com.willpower.tracker.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = AiAdviceEntity::class,
            parentColumns = ["id"],
            childColumns = ["adviceId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val techniqueName: String,
    val description: String,
    val recommendedDurationMin: Int,
    val difficulty: String,
    val category: String,
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val adviceId: String? = null
)
