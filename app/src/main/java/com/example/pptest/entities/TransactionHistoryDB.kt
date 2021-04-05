package com.example.pptest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionHistoryDB(

    @PrimaryKey
    var id: Int? = null,

    @ColumnInfo
    var usersId: Int,

    @ColumnInfo
    val title : String,

    @ColumnInfo
    val iconUrl : String,

    @ColumnInfo
    val date : String,

    @ColumnInfo
    val amount : String
)