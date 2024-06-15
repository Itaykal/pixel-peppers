package com.example.pixelpeppers.offlineCaching

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao

@Database(entities = arrayOf(Genre::class, User::class), version = 1, exportSchema = false)
abstract class PixelPeppersDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: PixelPeppersDatabase? = null

        fun getDatabase(context: Context): PixelPeppersDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PixelPeppersDatabase::class.java,
                    "pixel_peppers_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
