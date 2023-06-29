package com.edit.chatnex.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ClsListDataModel(

    val list: List<ArticleDataModel>

):Parcelable