package com.example.pixelpeppers.di

import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.ImageDao
import com.example.pixelpeppers.offlineCaching.daos.ReviewDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import com.example.pixelpeppers.repositories.GenreRepository
import com.example.pixelpeppers.repositories.ImageRepository
import com.example.pixelpeppers.repositories.ReviewRepository
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
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Singleton
    @Provides
    fun provideReviewRepository(reviewDao: ReviewDao): ReviewRepository {
        return ReviewRepository(reviewDao)
    }
    @Singleton
    @Provides
    fun provideImagesRepository(imageDao: ImageDao): ImageRepository {
        return ImageRepository(imageDao)
    }

}