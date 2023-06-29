package com.edit.chatnex.utils

import java.text.SimpleDateFormat
import java.util.*

class ParameterUtils {

    fun getCurrentTime():Long{
        val currentTimeMillis = System.currentTimeMillis() // 获取当前时间的毫秒数
        val unixTimestamp = currentTimeMillis / 1000 // 将毫秒数转换为秒数
        return unixTimestamp
    }

    fun getArticleTime(ctime:Long):String{
        val timestamp = 1687072645 // 时间戳
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // 创建SimpleDateFormat对象
        val date = Date(ctime * 1000L) // 将时间戳转换为Date对象
        val formattedDate = sdf.format(date) // 将Date对象格式化为指定格式的字符串
        return  formattedDate
    }
}