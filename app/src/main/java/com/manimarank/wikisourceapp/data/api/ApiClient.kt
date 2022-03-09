package com.manimarank.wikisourceapp.data.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun get(context: Context, code: String? = null): ApiServices {
        return  Retrofit.Builder()
            .baseUrl("https://${code ?: "en"}.wikisource.org/")
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServices::class.java)
    }
}