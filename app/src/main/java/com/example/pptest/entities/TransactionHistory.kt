package com.example.pptest.entities

data class TransactionHistory(
    val title : String,
    val iconUrl : String,
    val date : String,
    val balanceUsd : String,
    val balance: String,
    var balanceUsdSigned : String = "",
    var balanceSigned: String = ""
)