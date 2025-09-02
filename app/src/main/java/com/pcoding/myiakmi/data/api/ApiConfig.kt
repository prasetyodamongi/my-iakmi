package com.pcoding.myiakmi.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://iakmi.or.id/event/index.php/"
    private const val BASE_URL2 = "https://iakmi.or.id/register/index.php/"

    fun createApiClient(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun createApiClientMember(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}