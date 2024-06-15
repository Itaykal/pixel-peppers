package com.example.pixelpeppers.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.repositories.GenreRepository
import com.example.pixelpeppers.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel
@Inject constructor(
    private val repository: GenreRepository,
) : ViewModel() {
    val genres: LiveData<List<Genre>> = repository.genres

    fun refreshGenres() {
        viewModelScope.launch {
            repository.refreshGenres()
        }
    }
}
