package com.willpower.tracker.repository

import android.util.Log
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.database.CharacterEntity
import com.willpower.tracker.network.RetrofitClient
import com.willpower.tracker.network.models.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CharacterRepository(private val database: AppDatabase) {
    
    private val characterDao = database.characterDao()
    private val apiService = RetrofitClient.apiService
    
    val allCharacters: Flow<List<CharacterEntity>> = characterDao.getAllCharacters()
    
    suspend fun refreshCharacters() {
        withContext(Dispatchers.IO) {
            try {
                // Загружаем 3 страницы (60 персонажей)
                val page1 = apiService.getCharacters(1).body()?.results ?: emptyList()
                val page2 = apiService.getCharacters(2).body()?.results ?: emptyList()
                val page3 = apiService.getCharacters(3).body()?.results ?: emptyList()
                
                val allNetworkCharacters = page1 + page2 + page3
                
                val entities = allNetworkCharacters.map { it.toEntity() }
                
                characterDao.deleteAll()
                characterDao.insertAll(entities)
                
                Log.d("CharacterRepository", "Characters refreshed: ${entities.size}")
            } catch (e: Exception) {
                Log.e("CharacterRepository", "Error refreshing characters", e)
                throw e
            }
        }
    }
    
    suspend fun coldStart() {
        val count = characterDao.getCount()
        if (count == 0) {
            refreshCharacters()
        }
    }
    
    private fun Character.toEntity(): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            image = image,
            url = url,
            created = created
        )
    }
}
