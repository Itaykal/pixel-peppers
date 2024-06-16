package com.example.pixelpeppers.di

import com.example.pixelpeppers.clients.GameClient
import com.example.pixelpeppers.clients.UserClient
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.offlineCaching.daos.GameDao
import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.ReviewDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import com.example.pixelpeppers.repositories.GameRepository
import com.example.pixelpeppers.repositories.GenreRepository
import com.example.pixelpeppers.repositories.ReviewRepository
import com.example.pixelpeppers.repositories.UserRepository
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
object AppModule {

    @Singleton
    @Provides
    fun provideGenreRepository(genreDao: GenreDao, gameClient: GameClient): GenreRepository {
        return GenreRepository(genreDao, gameClient)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao, userClient: UserClient): UserRepository {
        return UserRepository(userDao, userClient)
    }

    @Singleton
    @Provides
    fun provideReviewRepository(reviewDao: ReviewDao): ReviewRepository {
        return ReviewRepository(reviewDao)
    }

    @Singleton
    @Provides
    fun provideGameRepository(gameDao: GameDao, gson: Gson, gameClient: GameClient): GameRepository {
        return GameRepository(gameDao, gson, gameClient)
    }
}