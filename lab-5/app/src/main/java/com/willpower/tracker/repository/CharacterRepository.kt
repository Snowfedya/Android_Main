package com.willpower.tracker.repository

import com.willpower.tracker.network.RetrofitClient
import com.willpower.tracker.network.models.Character

class CharacterRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun getCharacters(page: Int): List<Character> {
        val response = apiService.getCharacters(page)
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            throw Exception("Error fetching characters: ${response.code()}")
        }
    }
}
