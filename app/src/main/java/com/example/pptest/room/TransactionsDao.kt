package com.example.pptest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.UserDB

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: List<TransactionHistoryDB>)

    @Query("SELECT * FROM TransactionHistoryDB WHERE usersId ==:id")
    fun getTransactions(id: Int): LiveData<List<TransactionHistoryDB>>

    @Query("DELETE FROM TransactionHistoryDB")
    fun nukeTable()

}