package com.example.pptest.entities

import com.google.gson.annotations.SerializedName

data class Valute(
    @SerializedName("Value") val value : Double,
    val name: String
)