package com.willpower.tracker.repository

import com.willpower.tracker.BuildConfig
import com.willpower.tracker.network.RetrofitClient
import com.willpower.tracker.network.models.ChatRequest
import com.willpower.tracker.network.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String, val code: Int? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

class TechniqueRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun generateTechnique(
        challengeTitle: String,
        challengeCategory: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(challengeTitle, challengeCategory)
            
            val request = ChatRequest(
                messages = listOf(
                    Message(
                        role = "system",
                        content = "Ты эксперт по силе воли и самодисциплине, основываясь на методологии Келли МакГонигал. " +
                                "Давай практичные, конкретные советы на русском языке."
                    ),
                    Message(
                        role = "user",
                        content = prompt
                    )
                )
            )
            
            val authHeader = "Bearer ${BuildConfig.API_KEY}"
            val response = apiService.generateTechnique(authHeader, request)
            
            if (response.isSuccessful) {
                val body = response.body()
                val content = body?.choices?.firstOrNull()?.message?.content
                
                if (content != null) {
                    Result.Success(content)
                } else if (body?.error != null) {
                    Result.Error(body.error.message ?: "Неизвестная ошибка API")
                } else {
                    Result.Error("Пустой ответ от сервера")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Result.Error("Ошибка сервера: ${response.code()}", response.code())
            }
        } catch (e: Exception) {
            Result.Error("Ошибка сети: ${e.localizedMessage}")
        }
    }
    
    private fun buildPrompt(title: String, category: String): String {
        return """
            Опиши подробную технику выполнения челленджа "$title" из категории "$category".
            
            Структура ответа:
            1. Подготовка (что нужно сделать перед выполнением)
            2. Пошаговые действия (3-5 конкретных шагов)
            3. Советы для поддержания мотивации
            4. Связь с силой воли (как это укрепляет самоконтроль)
            
            Ответ должен быть практичным и мотивирующим.
        """.trimIndent()
    }
}
