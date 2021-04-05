package com.example.pptest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ValuteDb (
    @PrimaryKey
    val id: Int? = null,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val value : Double
)