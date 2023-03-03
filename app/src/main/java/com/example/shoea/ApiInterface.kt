package com.example.shoea

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val BASE_URL = "https://dummyjson.com/"

// Interface to call api
interface ApiInterface {
    @GET("products")
    fun getData() : Call<MODAL>
}

// Singleton Object of ApiInterface
object ApiServices{
    val newInstance: ApiInterface
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        newInstance = retrofit.create(ApiInterface::class.java)
    }
}