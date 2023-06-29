package com.edit.chatnex.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.edit.chatnex.model.LoginDataModel
import com.edit.chatnex.repository.AppRepository
import com.edit.chatnex.ui.NewsPagingSource
import com.google.gson.Gson

class NewsListViewModel: ViewModel() {

    val flow = Pager(
        //配置
        PagingConfig(pageSize = 20)
    ) {
        //我们自定义的数据源
        NewsPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}