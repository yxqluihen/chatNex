package com.edit.chatnex.repository

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

internal class AppGsonResponseConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonReader = gson.newJsonReader(value.charStream())
        return value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }

//            // TODO: 2021/7/12 向全局抛出API错误事件，待重构
//            (result as? ApiResponse<*>)?.let { apiResponse ->
//                apiResponse.stat
//                    .takeIf { ApiStatCode.SUCCESS.code != it }
//                    ?.let {
//                        ApiStatCode.valueOfApiStat(it)
//                    }?.run {
//                        postEvent(ApiStatCode.GLOBAL_API_ERROR_KEY, this, Bundle().apply {
//                            putString(AppBaseViewModel.S_API_MSG_KEY, apiResponse.msg)
//                        })
//                    }
//            }

            result
        }
    }
}