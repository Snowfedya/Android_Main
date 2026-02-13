package com.willpower.tracker.network.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AiAdvice(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val tags: List<String> = emptyList(),
    val mood: String = "motivational",
    val source: String = "glm-4.7-flash",
    val dateTime: Long = System.currentTimeMillis()
)
