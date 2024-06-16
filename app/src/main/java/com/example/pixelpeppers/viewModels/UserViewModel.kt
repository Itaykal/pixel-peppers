package com.example.pixelpeppers.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val user: LiveData<User> = userRepository.user

    fun startTwitchAuthActivity(context: Context) {
        userRepository.startTwitchAuthActivity(context)
    }

    fun loginWithCode(code: String) {
        viewModelScope.launch {
            userRepository.loginWithCode(code)
        }
    }


}