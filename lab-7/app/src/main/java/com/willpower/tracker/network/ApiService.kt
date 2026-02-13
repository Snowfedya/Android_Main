package com.willpower.tracker.network

import com.willpower.tracker.network.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): Response<CharacterResponse>
}
