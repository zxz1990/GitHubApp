package com.mcdull.githubapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OAuthClient {
    private const val BASE_URL = "https://github.com/"

    val apiService: OAuthApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(OAuthApi::class.java)
    }
}