package com.example.pixelpeppers.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.repositories.UserRepository
import com.example.pixelpeppers.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val user: LiveData<User> = userRepository.user

    fun loginWithCode(code: String) {
        println("Logging with code")
        viewModelScope.launch {
            userRepository.loginWithCode(code)
        }
    }


}