package com.edit.chatnex.repository

import com.edit.chatnex.BuildConfig

object AppUrlPath {
    //三层栏目数据列表
    const val APP_STYLE_PATH = "/app_style"

    //用户登录时获取短信验证码
    const val GET_AUTHORIZED_PATH = "/api/verification_code"

    //公用 api 接口
    const val COMMON_PATH = "/base/common_api"

    //文章列表接口
    const val DEPTHS_PATH = "/nodeapi/depths"

    //文章详情接口
    const val DETAIL_PATH = "/v3/article/detail"

    const val SAAS_HOST = "http://saas.zaker.cn/"

    const val CLS_HOST = "https://m.cls.cn"



}