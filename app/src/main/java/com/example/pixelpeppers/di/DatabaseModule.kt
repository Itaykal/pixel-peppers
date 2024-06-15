package com.example.pixelpeppers.di

import android.content.Context
import androidx.room.Room
import com.example.pixelpeppers.offlineCaching.PixelPeppersDatabase
import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideGenreDao(appDatabase: PixelPeppersDatabase): GenreDao {
        return appDatabase.genreDao()
    }

    @Provides
    fun provideUserDao(appDatabase: PixelPeppersDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): PixelPeppersDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PixelPeppersDatabase::class.java,
            "appDB"
        ).build()
    }

}