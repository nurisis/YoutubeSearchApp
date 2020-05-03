package com.hinuri.youtubesearchapp.di

import com.hinuri.data.YoutubeApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { createOkHttp() }

    single {
        createWebService<YoutubeApi>(get(), "https://www.googleapis.com/youtube/v3/")
    }
}

fun createOkHttp() : OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String) : T{
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        // For json parser
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}