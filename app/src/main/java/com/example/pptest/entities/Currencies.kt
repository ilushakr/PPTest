package com.example.pptest.entities

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Currencies(
    @SerializedName("Date") val date : String,
    @SerializedName("PreviousDate") val previousDate : String,
    @SerializedName("PreviousURL") val previousURL : String,
    @SerializedName("Timestamp") val timestamp : String,
//    @SerializedName("Valute") val valutes : ArrayList<Valute>
    @SerializedName("Valute")
    val valute: Map<String, Valute>
)