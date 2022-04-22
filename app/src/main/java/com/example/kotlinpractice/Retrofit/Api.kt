package com.example.kotlinpractice.Retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("entries")
    fun getData(): Call<JsonObject>

}