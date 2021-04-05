package com.example.pptest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(

    @PrimaryKey
    var id: Int? = null,

    @ColumnInfo
    var usersId: Int,

    @ColumnInfo
    var cardNumber: String,

    @ColumnInfo
    val type : String,

    @ColumnInfo
    val cardholderName : String,

    @ColumnInfo
    val valid : String,

    @ColumnInfo
    val balance : Double,

)