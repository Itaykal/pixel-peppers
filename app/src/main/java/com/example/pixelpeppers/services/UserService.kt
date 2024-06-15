package com.example.pixelpeppers.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.coordinators.dataCoordinator.updateAccessToken
import com.example.pixelpeppers.coordinators.dataCoordinator.updateTokenExpires
import com.example.pixelpeppers.extensions.await
import com.example.pixelpeppers.models.AccessTokenResponse
import com.example.pixelpeppers.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.functions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class UserService private constructor() {
    private val client = OkHttpClient().newBuilder().build()

    companion object {
        val instance: UserService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { UserService() }
        private const val USERS_COLLECTION = "users"
        private const val PIXEL_PEPPERS = "https://pixelpeppers.co.il"
        private const val ORIGIN_HEADER = "origin"
        private const val AUTHORIZE_URI = "https://id.twitch.tv/oauth2/authorize"
        private const val AUTH_REDIRECT_URI = "$PIXEL_PEPPERS/auth"
        private const val FUNCTIONS_URI = "https://us-central1-pixel-peppers.cloudfunctions.net/"
        private const val FUNCTION_NAME = "authenticateWithTwitch"
        private const val CLIENT_ID = "zpjv3uncv947n79ev1dvrq2vf8qkoo"
        private val functions: FirebaseFunctions = Firebase.functions
        private val collection = Firebase.firestore.collection(USERS_COLLECTION)
        private val auth = Firebase.auth
    }

    fun startTwitchAuthActivity(context: Context) {
        val uri = "$AUTHORIZE_URI?" +
                "client_id=$CLIENT_ID&" +
                "redirect_uri=$AUTH_REDIRECT_URI&" +
                "response_type=code&" +
                "scope=user_read"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    suspend fun getUser(id: String? = null): User {
        val id = id ?: auth.currentUser?.uid
        return User.fromFirestoreMap(
            collection
                .whereEqualTo("id", id)
                .get()
                .await()
                .toList()[0].data
        )
    }

    suspend fun authenticateWithCode(code: String): User {
        val customToken = getCustomAuthToken(code)
        auth.signInWithCustomToken(customToken).await()
        return getUser()
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
