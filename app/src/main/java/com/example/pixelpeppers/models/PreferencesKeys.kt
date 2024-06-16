package com.example.pixelpeppers.models

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object PreferencesKeys {
    val accessToken = stringPreferencesKey("access_token")
    val accessTokenExpirationTime = longPreferencesKey("access_token_expiration_time")
}