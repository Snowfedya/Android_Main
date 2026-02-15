package com.willpower.tracker.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id: String,
    val choices: List<Choice>,
    val created: Long
)

@Serializable
data class Choice(
    val index: Int,
    val message: ChatMessage,
    val finish_reason: String
)
