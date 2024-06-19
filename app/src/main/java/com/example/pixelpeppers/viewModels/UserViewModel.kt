package com.example.pixelpeppers.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.repositories.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    fun startTwitchAuthActivity(context: Context) {
        userRepository.startTwitchAuthActivity(context)
    }

    suspend fun loginWithCode(code: String) {
        userRepository.loginWithCode(code)
    }

    fun getUser(id: String? = null): LiveData<User> {
        if (id == null) {
            return userRepository.getUser(Firebase.auth.currentUser!!.uid)
        }
        return userRepository.getUser(id)
    }

    fun updateOnBoarding(id: String, value: Boolean) {
        viewModelScope.launch {
            userRepository.updateOnBoarding(id, value)
        }
    }

    fun refreshUser(id: String? = null) {
        viewModelScope.launch {
            if (id == null) {
                userRepository.refreshUser(Firebase.auth.currentUser!!.uid)
            } else {
                userRepository.refreshUser(id)
            }
        }
    }

    fun updateImage(id: String, imageURL: String) {
        viewModelScope.launch {
            userRepository.updateImage(id, imageURL)
        }
    }

    fun updateDisplayName(id: String, displayName: String) {
        viewModelScope.launch {
            userRepository.updateDisplayName(id, displayName)
        }
    }
}