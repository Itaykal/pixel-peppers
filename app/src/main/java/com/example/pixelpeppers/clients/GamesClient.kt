package com.example.pixelpeppers.clients

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private object RequestInterceptor : Interceptor {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val CLIENT_ID = "Client-ID"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Outgoing request to ${request.url}")
        val newRequest = request.newBuilder().addHeader("accept", "application/json")
            .addHeader(CLIENT_ID, "gvp0touvixyjwkce1yi3z37d1w8203")
            .addHeader(AUTHORIZATION_HEADER, "Bearer yis25y4dji506hc23xtcms1jgwfe4o").build()
        return chain.proceed(newRequest)
    }
}


object GamesClient {
    private const val BASE_URL = "https://api.igdb.com/v4"
    private val PLAIN = "text/plain".toMediaType()
    private val client = OkHttpClient().newBuilder().addInterceptor(RequestInterceptor).build()

    private suspend fun Call.await(): Response {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        continuation.resume(response)
                    } else {
                        continuation.resumeWithException(IOException("HTTP error code: ${response.code}"))
                    }
                }
            })
        }
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