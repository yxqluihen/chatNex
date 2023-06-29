package com.edit.chatnex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsListDataModel(
    @SerializedName("total_count")
    val totalCount: String? = null,
    @SerializedName("list")
    val list: ArrayList<NewsDataModel>? = null,
):Parcelable