package com.example.pptest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.UserDB

@Database(entities = [TransactionHistoryDB::class], version = 1)
abstract class TransactionsDatabase: RoomDatabase(){

    abstract fun transactionsDao(): TransactionsDao

    companion object{
        val NAME = "TRANSACTIONS_DB"
    }
}