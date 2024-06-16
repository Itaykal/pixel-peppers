package com.example.pixelpeppers.services

import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.extensions.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

private object RequestInterceptor : Interceptor {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val CLIENT_ID_HEADER = "Client-ID"
    private const val CLIENT_ID = "zpjv3uncv947n79ev1dvrq2vf8qkoo"

    private fun isAccessTokenExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = DataCoordinator.instance.accessTokenExpirationTime
        return currentTime >= expirationTime
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var accessToken = DataCoordinator.instance.accessToken

        if (accessToken == null || isAccessTokenExpired()) {
            // Make the token refresh request
            val refreshedToken = runBlocking {
                UserService.instance.refreshAccessToken()
            }

            accessToken = refreshedToken.accessToken
        }

        val newRequest = originalRequest.newBuilder().addHeader("accept", "application/json")
            .addHeader(CLIENT_ID_HEADER, CLIENT_ID)
            .addHeader(AUTHORIZATION_HEADER, "Bearer ${accessToken}").build()
        return chain.proceed(newRequest)
    }
}


class GamesService {
    companion object {
        val instance: GamesService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GamesService() }
        private const val BASE_URL = "https://api.igdb.com/v4"
        private val PLAIN = "text/plain".toMediaType()
        private val client = OkHttpClient().newBuilder().addInterceptor(RequestInterceptor).build()
    }
    suspend fun getGenres(limit: Int = 18): Response = withContext(Dispatchers.IO) {
        val body = "limit $limit; fields name;".toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/genres").post(body).build()
        client.newCall(request).await()
    }

    suspend fun searchGames(search: String = "", limit: Int = 8): Response =
        withContext(Dispatchers.IO) {
            var query = "limit $limit; fields name,id,genres.name,genres.id,cover.url;"
            if (search != "") {
                query += " search \"$search\";"
            }
            val body = query.toRequestBody(PLAIN)
            val request = Request.Builder().url("$BASE_URL/games").post(body).build()
            client.newCall(request).await()
        }

    suspend fun getGame(gameId: Int): Response = withContext(Dispatchers.IO) {
        val query =
            "where id=$gameId; fields name,id,genres.name,genres.id,cover.url,summary,similar_games;"
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/games").post(body).build()
        client.newCall(request).await()
    }

    suspend fun getCoverURL(gameId: Int): Response = withContext(Dispatchers.IO) {
        val query = "where game=$gameId; fields url;"
        val body = query.toRequestBody(PLAIN)
        val request = Request.Builder().url("$BASE_URL/covers").post(body).build()
        client.newCall(request).await()
    }
}