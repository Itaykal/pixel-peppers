package com.example.pixelpeppers.offlineCaching

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.models.Image
import com.example.pixelpeppers.models.Review
import com.example.pixelpeppers.models.User
import com.example.pixelpeppers.offlineCaching.daos.GameDao
import com.example.pixelpeppers.offlineCaching.daos.GenreDao
import com.example.pixelpeppers.offlineCaching.daos.ImageDao
import com.example.pixelpeppers.offlineCaching.daos.ReviewDao
import com.example.pixelpeppers.offlineCaching.daos.UserDao

@Database(
    entities = [Genre::class, User::class, Review::class, Image::class, Game::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PixelPeppersDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao
    abstract fun imageDao(): ImageDao
    abstract fun gameDao(): GameDao

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
