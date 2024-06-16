package com.example.pixelpeppers.offlineCaching.daos;

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixelpeppers.models.User

@Dao
interface UserDao {
    @Query("select * from users where id = :id limit 1")
    fun getUser(id: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("update users set onboarding_complete = :onBoardingComplete where id = :userID")
    fun updateOnBoarding(userID: String, onBoardingComplete: Boolean)
}
