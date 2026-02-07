package com.willpower.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.willpower.tracker.database.AppDatabase
import com.willpower.tracker.database.ChallengeEntity
import com.willpower.tracker.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val repository = ChallengeRepository(database)
    
    val allChallenges: StateFlow<List<ChallengeEntity>> = repository.allChallenges
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val completedChallenges: StateFlow<List<ChallengeEntity>> = repository.completedChallenges
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val pendingChallenges: StateFlow<List<ChallengeEntity>> = repository.pendingChallenges
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val totalCount: StateFlow<Int> = repository.totalCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    val completedCount: StateFlow<Int> = repository.completedCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    val todayCompletions: StateFlow<Int> = repository.getTodayCompletions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    val allCategories: StateFlow<List<String>> = repository.allCategories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    val challengesPaged: Flow<PagingData<ChallengeEntity>> = repository.getChallengesPaged()
        .cachedIn(viewModelScope)
    
    fun getChallengeById(id: Int): Flow<ChallengeEntity?> {
        return repository.getChallengeById(id)
    }
    
    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun searchChallenges(query: String): Flow<List<ChallengeEntity>> {
        return repository.searchChallenges(query)
    }
    
    fun addChallenge(challenge: ChallengeEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.addChallenge(challenge)
            _isLoading.value = false
        }
    }
    
    fun updateChallenge(challenge: ChallengeEntity) {
        viewModelScope.launch {
            repository.updateChallenge(challenge)
        }
    }
    
    fun deleteChallenge(challenge: ChallengeEntity) {
        viewModelScope.launch {
            repository.deleteChallenge(challenge)
        }
    }
    
    fun completeChallenge(challengeId: Int, notes: String? = null) {
        viewModelScope.launch {
            repository.completeChallenge(challengeId, notes)
        }
    }
    
    fun uncompleteChallenge(challengeId: Int) {
        viewModelScope.launch {
            repository.uncompleteChallenge(challengeId)
        }
    }
    
    fun resetDailyProgress() {
        viewModelScope.launch {
            repository.resetDailyProgress()
        }
    }
    
    fun performColdStart() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.coldStart()
            _isLoading.value = false
        }
    }
}
