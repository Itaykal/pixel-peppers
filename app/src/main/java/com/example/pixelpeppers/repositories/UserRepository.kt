package com.example.pixelpeppers.repositories

import androidx.lifecycle.LiveData
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import com.example.pixelpeppers.services.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {
    val user: LiveData<User> = userDao.getUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun loginWithCode(code: String) {
        try {
            val user = UserService.instance.authenticateWithCode(code)
            coroutineScope.launch(Dispatchers.IO) {
                userDao.insert(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}