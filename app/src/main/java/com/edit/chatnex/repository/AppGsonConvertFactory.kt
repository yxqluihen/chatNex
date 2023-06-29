package com.edit.chatnex.repository

import com.edit.chatnex.repository.AppGsonResponseConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AppGsonConvertFactory private constructor(private val gson: Gson) : Converter.Factory() {

    companion object {
        fun create(gson: Gson = Gson()) = AppGsonConvertFactory(gson)
    }

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return AppGsonResponseConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return AppGsonRequestBodyConverter(gson, adapter)
    }

}