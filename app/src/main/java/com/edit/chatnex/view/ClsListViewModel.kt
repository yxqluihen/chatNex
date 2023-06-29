package com.edit.chatnex.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.edit.chatnex.ui.ClsPagingSource


class ClsListViewModel: ViewModel() {

    val flow = Pager(
        //配置
        PagingConfig(pageSize = 20, prefetchDistance = 2,initialLoadSize = 20)
    ) {
        //我们自定义的数据源
        ClsPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}