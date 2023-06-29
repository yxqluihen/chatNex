package com.edit.chatnex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagDataModel(
    @SerializedName("category_id")
    val categoryId: Int,
    val color: String,
    val id: Int,
    val name: String
): Parcelable