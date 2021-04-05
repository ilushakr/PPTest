package com.example.pptest.entities

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("users") val users: List<User>
)