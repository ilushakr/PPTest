package com.example.pptest.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("card_number") val cardNumber : String,
    @SerializedName("type") val type : String,
    @SerializedName("cardholder_name") val cardholderName : String,
    @SerializedName("valid") val valid : String,
    @SerializedName("balance") val balance : Double,
    @SerializedName("transaction_history") val transactionHistoryApi : List<TransactionHistoryApi>
)