package com.example.pptest.entities

import com.google.gson.annotations.SerializedName

data class TransactionHistoryApi(
    @SerializedName("title") val title : String,
    @SerializedName("icon_url") val iconUrl : String,
    @SerializedName("date") val date : String,
    @SerializedName("amount") val amount : String
)