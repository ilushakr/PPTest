package com.example.pptest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pptest.entities.ValuteDb

@Database(entities = [ValuteDb::class], version = 1)
abstract class ValuteDatabase: RoomDatabase() {

    abstract fun valuteDao(): ValuteDao

    companion object{
        val NAME = "VALUTE_DB"
    }

}

