package com.example.pptest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.ValuteDb

@Dao
interface ValuteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValutes(valutes: List<ValuteDb>)

    @Query("SELECT * FROM ValuteDb")
    fun getValutes(): LiveData<List<ValuteDb>>
}