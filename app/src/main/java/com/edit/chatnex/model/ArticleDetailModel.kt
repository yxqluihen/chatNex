package com.edit.chatnex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleDetailModel(
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("brief")
    val brief: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("ctime")
    var ctime:String
): Parcelable
