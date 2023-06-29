package com.edit.chatnex.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataStoreUtils {
    val gson = Gson()

    inline fun <reified T> fromJson(json: String): T {
        //如果T为泛型，则加此行代码
        val type = object : TypeToken<T>() {}.type
        //如果T不是泛型，则直接返回
//        return gson.fromJson(json, T::class.java)
        return gson.fromJson(json, type)
    }

    fun <T> toJson(data: T): String {
        return gson.toJson(data)
    }
}