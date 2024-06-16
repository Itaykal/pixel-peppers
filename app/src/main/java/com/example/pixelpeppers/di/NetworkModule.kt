package com.example.pixelpeppers.di

import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.clients.GameClient
import com.example.pixelpeppers.clients.UserClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideGameClient(
        interceptor: Interceptor,
        okHttpClient: OkHttpClient
    ): GameClient {
        val interceptedOkHttpClient = okHttpClient.newBuilder().addInterceptor(interceptor).build()
        return GameClient(interceptedOkHttpClient)
    }

    @Singleton
    @Provides
    fun provideUserClient(
        okHttpClient: OkHttpClient
    ): UserClient {
        return UserClient(okHttpClient)
    }

    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    fun getInterceptor(userClient: UserClient): Interceptor {
        return object : Interceptor {
            private val AUTHORIZATION_HEADER = "Authorization"
            private val CLIENT_ID_HEADER = "Client-ID"
            private val CLIENT_ID = "zpjv3uncv947n79ev1dvrq2vf8qkoo"

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
                        userClient.refreshAccessToken()
                    }

                    accessToken = refreshedToken.accessToken
                }

                val newRequest =
                    originalRequest.newBuilder().addHeader("accept", "application/json")
                        .addHeader(CLIENT_ID_HEADER, CLIENT_ID)
                        .addHeader(AUTHORIZATION_HEADER, "Bearer ${accessToken}").build()
                return chain.proceed(newRequest)
            }
        }
    }
}