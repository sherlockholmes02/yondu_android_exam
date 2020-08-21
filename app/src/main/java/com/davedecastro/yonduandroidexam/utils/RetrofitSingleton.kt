package com.davedecastro.yonduandroidexam.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    val gson = GsonBuilder()
        .create()

    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-52-76-75-52.ap-southeast-1.compute.amazonaws.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    inline fun <reified T> get(): T =
        retrofit.create(T::class.java)
}

fun Any?.toJson() =
    RetrofitSingleton.gson.toJson(this)

inline fun <reified T> String.fromJson() =
    RetrofitSingleton.gson.fromJson(this, T::class.java)