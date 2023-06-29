package com.edit.chatnex.repository

enum class ApiStatCode(var code: String) {

    /**
     * 接口请求成功
     */
    SUCCESS("1"),

    /**
     * 接口请求失败
     */
    FAILED("0"),

    /**
     * 用户未授权，提示用户登录或者提示跳转登录页面，具体根据业务进行处理
     */
    UNAUTHORIZED("10014"),

    /**
     * 需进行手机号绑定后才可进行操作，此时需跳转到手机号绑定页面
     */
    NEED_BIND_PHONE("10016")
    ;

    companion object {

        const val GLOBAL_API_ERROR_KEY = "global_api_error_key"

        fun valueOfApiStat(stat: String) =
            values().find {
                it.code == stat
            } ?: FAILED
    }
}