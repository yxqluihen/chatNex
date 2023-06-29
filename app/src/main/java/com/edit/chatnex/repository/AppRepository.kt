package com.edit.chatnex.repository

import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppRepository {

    companion object {

        val INSTANCE: AppRepository by lazy { AppRepository() }

//        private const val cacheSize = 100 * 1024 * 1024L // 100 MB
//
//        private val appCache: Cache by lazy {
//            Cache(File(RmtApplication.instance.cacheDir, "http-cache"), cacheSize)
//        }
//
//        private val appResponseCache by lazy {
//            val customFile = File(RmtApplication.instance.cacheDir, "custom-http-cache")
//            AppCache(customFile, cacheSize)
//        }

        fun getApiService(baseURL:String): ApiService = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(
                AppGsonConvertFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .client(
                OkHttpClient.Builder()
//                    .cache(appCache)
                    .addInterceptor(LogInterceptor())
                    .callTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }
}