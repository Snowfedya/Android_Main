package com.willpower.tracker.network

import com.willpower.tracker.network.models.ChatRequest
import com.willpower.tracker.network.models.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    
    @POST("chat/completions")
    suspend fun generateTechnique(
        @Header("Authorization") authHeader: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}
