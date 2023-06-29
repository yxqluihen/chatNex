package com.edit.chatnex.repository

import android.os.Parcelable
import com.edit.chatnex.repository.NetErrorHelper.getLocalMessage
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse<T : Parcelable>(
    val stat: String,
    val data: T?,
    val msg: String

):Parcelable{
    companion object {
        fun <T : Parcelable> isSuccess(response: ApiResponse<T>?): Boolean =
            response != null && "1" == response.stat

        fun ApiResponse<*>?.isExpiredAuthorized() =
            this != null && ApiStatCode.UNAUTHORIZED.code == stat

        fun <T : Parcelable> createFail(throwable: Throwable) =
            ApiResponse<T>("-1", null, throwable.getLocalMessage())
    }

    suspend fun onFail2(block: suspend ApiResponse<T>.() -> Unit): ApiResponse<T> {
        if (!isSuccess(this)) {
            this.block()
        }
        return this
    }

    suspend fun onResult2(block: suspend ApiResponse<T>.(isSuccess: Boolean) -> Unit): ApiResponse<T> {
        this.block(isSuccess(this))
        return this
    }

    fun onSuccess(block: ApiResponse<T>.() -> Unit): ApiResponse<T> {
        if (isSuccess(this)) {
            this.block()
        }
        return this
    }

    fun onFail(block: ApiResponse<T>.() -> Unit): ApiResponse<T> {
        if (!isSuccess(this)) {
            this.block()
        }
        return this
    }

    fun onResult(block: ApiResponse<T>.(isSuccess: Boolean) -> Unit): ApiResponse<T> {
        this.block(isSuccess(this))
        return this
    }

}
