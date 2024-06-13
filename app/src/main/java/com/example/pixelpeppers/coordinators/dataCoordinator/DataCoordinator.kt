package com.example.pixelpeppers.coordinators.dataCoordinator

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataCoordinator private constructor() {
    companion object
    {
        val instance: DataCoordinator by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DataCoordinator() }
    }
    var context: Context? = null

    var accessToken: String? = null
    val defaultAccessTokenValue: String? = null

    var accessTokenExpirationTime: Long? = null
    val defaultAccessTokenExpirationTimeValue: Long? = null

    var username: String? = null
    val defaultUsernameValue: String? = null

    private val USER_PREFERENCES_NAME = "token"
    val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    @OptIn(DelicateCoroutinesApi::class)
    fun initialize(context: Context, onLoad: () -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            initializeAsync(context)
            onLoad()
        }
    }

    suspend fun initializeAsync(context: Context) {
        this.context = context
        username = getUsername()
        accessToken = getAccessToken()
        accessTokenExpirationTime = getAccessTokenExpirationTime()
    }
}