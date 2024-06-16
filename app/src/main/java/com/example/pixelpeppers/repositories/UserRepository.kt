package com.example.pixelpeppers.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import com.example.pixelpeppers.clients.UserClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(
    private val userDao: UserDao,
    private val userClient: UserClient,
) {
    val user: LiveData<User> = userDao.getUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun startTwitchAuthActivity(context: Context) {
        userClient.startTwitchAuthActivity(context)
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
}