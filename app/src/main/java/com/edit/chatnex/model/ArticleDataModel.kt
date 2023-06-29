package com.edit.chatnex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArticleDataModel(

    @SerializedName("article_id")
    val articleId: Int,
    @SerializedName("article_rec")
    val articleRec: List<ArticleRecModel>,
    val brief: String,
    val ctime: Long,
    val img: String,
    @SerializedName("reading_num")
    val readingNum: Int,
    @SerializedName("sort_score")
    val sortScore: Int,
    val tags: List<TagDataModel>,
    val title: String
): Parcelable