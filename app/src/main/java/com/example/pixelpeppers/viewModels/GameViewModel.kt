package com.example.pixelpeppers.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.repositories.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel
@Inject constructor(
    private val repository: GameRepository,
) : ViewModel() {

    private var searchJob: Job? = null

    fun refreshGamesByGenre(genre: String) {
        viewModelScope.launch {
            repository.refreshGamesByGenre(genre)
        }
    }

    fun getGamesByGenre(genre: String) = repository.getGamesByGenre(genre)

    fun getGameById(gameID: Int) = repository.getGameById(gameID)

    fun refreshGamesByID(gameID: Int) {
        viewModelScope.launch {
            repository.refreshGameByID(gameID)
        }
    }

    fun refreshGamesBySearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            repository.refreshGamesBySearch(query)
        }
    }

    fun getGamesBySearch(query: String) = repository.getGamesBySearch(query)
}