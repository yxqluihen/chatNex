package com.edit.chatnex.repository

import com.edit.chatnex.repository.ApiService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.util.concurrent.TimeUnit

//响应数据以ResponseBody类，ApiService接口的方法返回类型Call<ResponseBody>
class RetrofitRepository {
    companion object{
        val INSTANCE: RetrofitRepository by lazy { RetrofitRepository() }



        fun getApiService(): ApiService = Retrofit.Builder()
            .baseUrl("http://121.9.213.58/")
            .addConverterFactory(
                GsonConverterFactory.create(
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