package com.edit.chatnex.repository

import com.edit.chatnex.MyApplication
import com.edit.chatnex.R
import java.io.InterruptedIOException
import java.net.UnknownHostException
import retrofit2.HttpException

object NetErrorHelper {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504

    fun Throwable.getLocalMessage() = MyApplication.instance.resources.let { res ->
        when (val error = this) {
            is HttpException -> when (error.code()) {
                UNAUTHORIZED,
                FORBIDDEN,
                NOT_FOUND,
                REQUEST_TIMEOUT,
                GATEWAY_TIMEOUT,
                INTERNAL_SERVER_ERROR,
                BAD_GATEWAY,
                SERVICE_UNAVAILABLE
                -> res.getString(R.string.error_msg_api_exception)
                else -> null
            }
            is UnknownHostException -> res.getString(R.string.error_msg_network_exception)
            is InterruptedIOException -> res.getString(R.string.error_msg_network_timeout)
            else -> null
        } ?: res.getString(R.string.error_msg_api_exception)
    }
}