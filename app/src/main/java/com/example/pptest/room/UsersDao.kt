package com.example.pptest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pptest.entities.UserDB

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(post: List<UserDB>)

    @Query("SELECT * FROM UserDB WHERE cardNumber ==:usersCardNumber")
    fun getUser(usersCardNumber: String): LiveData<UserDB>

    @Query("SELECT * FROM UserDB WHERE id ==:id")
    fun getUser(id: Int): LiveData<UserDB>

    @Query("SELECT * FROM UserDB")
    fun getUsers(): LiveData<List<UserDB>>

    @Query("DELETE FROM UserDB")
    fun nukeTable()

}