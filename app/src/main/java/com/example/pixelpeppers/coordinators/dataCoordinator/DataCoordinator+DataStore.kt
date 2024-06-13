package com.example.pixelpeppers.coordinators.dataCoordinator

import androidx.datastore.preferences.core.edit
import com.example.pixelpeppers.models.PreferencesKeys
import kotlinx.coroutines.flow.firstOrNull


suspend fun DataCoordinator.setAccessToken(accessToken: String) {
    val context = this.context ?: return
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.accessToken] = accessToken
    }
}

suspend fun DataCoordinator.getAccessToken(): String? {
    val context = this.context ?: return defaultAccessTokenValue
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.accessToken)
        ?: defaultAccessTokenValue
}

suspend fun DataCoordinator.setAccessTokenExpirationTime(time: Long) {
    val context = this.context ?: return
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.accessTokenExpirationTime] = time
    }
}

suspend fun DataCoordinator.getAccessTokenExpirationTime(): Long? {
    val context = this.context ?: return defaultAccessTokenExpirationTimeValue
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.accessTokenExpirationTime)
        ?: defaultAccessTokenExpirationTimeValue
}

suspend fun DataCoordinator.setUsername(username: String) {
    val context = this.context ?: return
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.usernameKey] = username
    }
}

suspend fun DataCoordinator.getUsername(): String? {
    val context = this.context ?: return defaultUsernameValue
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.usernameKey)
        ?: defaultUsernameValue
}
