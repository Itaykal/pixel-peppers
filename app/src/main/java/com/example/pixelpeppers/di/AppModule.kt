package com.example.pixelpeppers.di

import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import com.example.pixelpeppers.repositories.GenreRepository
import com.example.pixelpeppers.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGenreRepository(genreDao: GenreDao): GenreRepository {
        return GenreRepository(genreDao)
    }

    @Singleton
    @Provides
    fun provideUserService(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
}