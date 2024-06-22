package com.example.pixelpeppers.coordinators.dataCoordinator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun DataCoordinator.updateAccessToken(accessToken: String) {
    this.accessToken = accessToken
    GlobalScope.launch(Dispatchers.Default) {
        setAccessToken(accessToken)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun DataCoordinator.updateTokenExpires(expiresIn: Int) {
    val expirationTime = System.currentTimeMillis() + expiresIn * 1000
    this.accessTokenExpirationTime = expirationTime
    GlobalScope.launch(Dispatchers.Default) {
        setAccessTokenExpirationTime(expirationTime)
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun DataCoordinator.clearData() {
    GlobalScope.launch(Dispatchers.Default) {
        clearAll()
    }
}