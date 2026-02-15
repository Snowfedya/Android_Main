package com.willpower.tracker.repository

import com.willpower.tracker.network.ApiService
import com.willpower.tracker.network.RetrofitClient
import com.willpower.tracker.network.models.AiAdvice
import com.willpower.tracker.network.models.ChatMessage
import com.willpower.tracker.network.models.ChatRequest

class AdviceRepository() {
    private val apiService: ApiService = RetrofitClient.apiService

    private val prompts = listOf(
        "Give me a short motivational quote about willpower and procrastination in Russian.",
        "Provide a brief tip for building better habits and focus in Russian.",
        "Share an encouraging message for someone struggling with productivity in Russian."
    )

    suspend fun getRandomAdvice(): Result<AiAdvice> = try {
        val prompt = prompts.random()
        val request = ChatRequest(
            messages = listOf(ChatMessage(role = "user", content = prompt))
        )
        val response = apiService.getAdvice(request)
        if (response.isSuccessful && response.body() != null) {
            val adviceText = response.body()!!.choices.firstOrNull()?.message?.content
                ?: "Сделай 2 минуты прямо сейчас!"
            Result.success(AiAdvice(text = adviceText))
        } else {
            Result.success(AiAdvice(text = "Сделай 2 минуты прямо сейчас!"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
