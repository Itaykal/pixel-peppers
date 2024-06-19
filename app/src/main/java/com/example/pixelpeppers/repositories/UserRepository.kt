package com.example.pixelpeppers.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pixelpeppers.clients.UserClient
import com.example.pixelpeppers.models.UpdateUser
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(
    private val userDao: UserDao,
    private val userClient: UserClient,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun startTwitchAuthActivity(context: Context) {
        userClient.startTwitchAuthActivity(context)
    }

    fun getUser(id: String): LiveData<User> {
        return userDao.getUser(id)
    }

    suspend fun refreshUser(id: String) {
        try {
            val user = userClient.getUser(id)
            coroutineScope.launch(Dispatchers.IO) {
                userDao.insert(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun loginWithCode(code: String) {
        try {
            val user = userClient.authenticateWithCode(code)
            coroutineScope.launch(Dispatchers.IO) {
                userDao.insert(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateOnBoarding(userId: String, value: Boolean) {
        userClient.updateUser(userId, UpdateUser(onboardingComplete = value))
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateOnBoarding(userId, value)
        }
    }

    suspend fun updateImage(id: String, imageURL: String) {
        userClient.updateUser(id, UpdateUser(profileImageUrl = imageURL))
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateUserProfileImage(id, imageURL)
        }
    }

    suspend fun updateDisplayName(id: String, displayName: String) {
        userClient.updateUser(id, UpdateUser(displayName = displayName))
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateDisplayName(id, displayName)
        }

    }
}