package com.edit.chatnex.repository

import android.os.Parcelable
import com.edit.chatnex.repository.NetErrorHelper.getLocalMessage
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsApiResponse<T : Parcelable>(
    val errno: String,
    val data: T?

):Parcelable{
    companion object {
        fun <T : Parcelable> isSuccess(response: NewsApiResponse<T>?): Boolean =
            response != null && "0" == response.errno

        fun NewsApiResponse<*>?.isExpiredAuthorized() =
            this != null && ApiStatCode.UNAUTHORIZED.code == errno

//        fun <T : Parcelable> createFail(throwable: Throwable) =
//            NewsApiResponse<T>("-1", null, throwable.getLocalMessage())

    }

    suspend fun onFail2(block: suspend NewsApiResponse<T>.() -> Unit): NewsApiResponse<T> {
        if (!isSuccess(this)) {
            this.block()
        }
        return this
    }

    suspend fun onResult2(block: suspend NewsApiResponse<T>.(isSuccess: Boolean) -> Unit): NewsApiResponse<T> {
        this.block(isSuccess(this))
        return this
    }

    fun onSuccess(block: NewsApiResponse<T>.() -> Unit): NewsApiResponse<T> {
        if (isSuccess(this)) {
            this.block()
        }
        return this
    }

    fun onFail(block: NewsApiResponse<T>.() -> Unit): NewsApiResponse<T> {
        if (!isSuccess(this)) {
            this.block()
        }
        return this
    }

    fun onResult(block: NewsApiResponse<T>.(isSuccess: Boolean) -> Unit): NewsApiResponse<T> {
        this.block(isSuccess(this))
        return this
    }

}
