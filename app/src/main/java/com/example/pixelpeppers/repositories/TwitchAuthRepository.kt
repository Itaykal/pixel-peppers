package com.example.pixelpeppers.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.coordinators.dataCoordinator.updateAccessToken
import com.example.pixelpeppers.coordinators.dataCoordinator.updateTokenExpires
import com.example.pixelpeppers.extensions.await
import com.example.pixelpeppers.models.AccessTokenResponse
import com.google.firebase.Firebase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.functions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class TwitchAuthRepository private constructor() {
    private val client = OkHttpClient().newBuilder().build()

    companion object {
        const val PIXEL_PEPPERS = "https://pixelpeppers.co.il"
        const val ORIGIN_HEADER = "origin"
        const val AUTHORIZE_URI = "https://id.twitch.tv/oauth2/authorize"
        const val AUTH_REDIRECT_URI = "${PIXEL_PEPPERS}/auth"
        const val FUNCTIONS_URI = "https://us-central1-pixel-peppers.cloudfunctions.net/"
        const val FUNCTION_NAME = "authenticateWithTwitch"
        const val CLIENT_ID = "zpjv3uncv947n79ev1dvrq2vf8qkoo"
        val instance: TwitchAuthRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { TwitchAuthRepository() }
        val functions: FirebaseFunctions = Firebase.functions
    }

    fun startTwitchAuthActivity(context: Context) {
        val uri = "$AUTHORIZE_URI?" +
                "client_id=$CLIENT_ID&" +
                "redirect_uri=${AUTH_REDIRECT_URI}&" +
                "response_type=code&" +
                "scope=user_read"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    suspend fun authenticateWithTwitch(code: String, onSuccess: (token: String) -> Unit) =
        withContext(Dispatchers.IO) {
            val customAuthToken = getCustomAuthToken(code)
            onSuccess(customAuthToken)
        }

    private suspend fun getCustomAuthToken(code: String): String = withContext(Dispatchers.IO) {
        val url = "$FUNCTIONS_URI/$FUNCTION_NAME?" +
                "code=$code"
        val request = Request.Builder()
            .url(url)
            .addHeader(ORIGIN_HEADER, PIXEL_PEPPERS)
            .build()
        client.newCall(request).await().body?.string() ?: ""
    }

    suspend fun refreshAccessToken(): AccessTokenResponse = withContext(Dispatchers.IO) {
        val result = functions
            .getHttpsCallable("getTwitchAccessToken")
            .call()
            .await()
        val accessTokenResponse = AccessTokenResponse.fromMap(result.data as HashMap<*, *>)
        DataCoordinator.instance.updateAccessToken(accessTokenResponse.accessToken)
        DataCoordinator.instance.updateTokenExpires(accessTokenResponse.expiresIn)
        accessTokenResponse
    }
}
