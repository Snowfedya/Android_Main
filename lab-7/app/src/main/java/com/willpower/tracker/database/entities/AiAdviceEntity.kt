package com.willpower.tracker.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_advice")
data class AiAdviceEntity(
    @PrimaryKey val id: String,
    val text: String,
    val tags: String,  // JSON array as string
    val mood: String,
    val source: String,
    val dateTime: Long
)
