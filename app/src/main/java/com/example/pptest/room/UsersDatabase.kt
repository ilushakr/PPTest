package com.example.pptest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pptest.entities.UserDB

@Database(entities = [UserDB::class], version = 1)
abstract class UsersDatabase: RoomDatabase(){

    abstract fun usersDao(): UsersDao

    companion object{
        val NAME = "USERS_DB"
    }
}