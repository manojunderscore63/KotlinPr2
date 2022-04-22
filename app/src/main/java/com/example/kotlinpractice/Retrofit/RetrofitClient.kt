package com.example.kotlinpractice.Retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        private var mInstance: RetrofitClient = RetrofitClient()
        private lateinit var retrofit: Retrofit

        @Synchronized fun getInstance(): RetrofitClient{
            if (mInstance==null){
                mInstance = RetrofitClient()
            }
            return mInstance
        }
    }

    init{
        retrofit = Retrofit.Builder().
                baseUrl("https://api.publicapis.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    public fun getApi(): Api{
        return retrofit.create(Api::class.java)
    }

}