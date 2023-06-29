package com.edit.chatnex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDataModel(
    @SerializedName("app_logo")
    val appLogo: String,
    @SerializedName("app_name")
    val appName: String,
    @SerializedName("article_md5")
    val articleMD5: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("del_tags")
    val delTags: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("identification")
    val identification: String,
    @SerializedName("important")
    val important: String,
    @SerializedName("important_get")
    val importantGet: String,
    @SerializedName("kw")
    val kw: String,
    @SerializedName("media")
    val media: String,
    @SerializedName("medias")
    val medias: String,
    @SerializedName("order_time")
    val orderTime: String,
    @SerializedName("parent_id")
    val parentId: String,
    @SerializedName("repeat_media")
    val repeatMedia: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("time")
    val time: String,
    @SerializedName("time_fat")
    val timeFat: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String

):Parcelable


