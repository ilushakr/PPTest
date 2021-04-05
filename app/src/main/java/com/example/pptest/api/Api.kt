package com.example.pptest.api

import com.example.pptest.entities.Currencies
import com.example.pptest.entities.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface Api {

    @GET("users.json")
    fun getUsers(): Call<Users>

    @GET()
    fun getCurrency(@Url url: String): Call<Currencies>

//    @GET
//    fun getUser(@Url url: String): Call<User>

}