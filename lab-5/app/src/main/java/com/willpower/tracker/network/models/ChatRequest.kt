package com.willpower.tracker.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val model: String = "glm-4.7-flash",
    val messages: List<ChatMessage>
)

@Serializable
data class ChatMessage(
    val role: String,  // "user" or "assistant"
    val content: String
)
