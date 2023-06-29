package com.edit.chatnex.ui

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edit.chatnex.model.*
import com.edit.chatnex.repository.ApiResponse
import com.edit.chatnex.repository.AppRepository
import com.edit.chatnex.repository.AppUrlPath
import com.edit.chatnex.repository.NewsApiResponse
import com.edit.chatnex.utils.ParameterUtils
import com.google.gson.Gson

class ClsPagingSource:
    PagingSource<Int, ArticleDataModel>() {

    var cls : NewsApiResponse<ClsListDataModel>? = null
    var lastId :Long = ParameterUtils().getCurrentTime()

    override fun getRefreshKey(state: PagingState<Int, ArticleDataModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDataModel> {
        try {
            var list: List<ArticleDataModel>? = null
            var page = params.key ?: 1  //当前页码，若为空则设为1
            val pageSize = params.loadSize  //每次加载的数目
            cls = AppRepository.getApiService(AppUrlPath.CLS_HOST).getClsList(AppUrlPath.DEPTHS_PATH,
                ParameterUtils().getCurrentTime())
            if (page == 1){
                cls = AppRepository.getApiService(AppUrlPath.CLS_HOST).getClsList(AppUrlPath.DEPTHS_PATH,lastId)
                lastId = cls?.data?.list?.last()?.ctime!!
            }else{
                cls = AppRepository.getApiService(AppUrlPath.CLS_HOST).getClsList(AppUrlPath.DEPTHS_PATH,lastId)
                lastId = cls?.data?.list?.last()?.ctime!!
            }
            val prevKey = if (page > 1) page - 1 else null   //计算上一页的页码
            val nextKey =
                if (cls?.data?.list != null) page + 1 else null //计算下一页的页码
            //将数据和页码设置到LoadResult中
            list = cls?.data?.list
            Log.d("ClsListDataModel=","${cls?.data?.list}")
            return LoadResult.Page(
                data = list as ArrayList<ArticleDataModel>,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }


    }
    //如果需要从中间开始加载的话，就按如下方法即可。
//    override fun getRefreshKey(state: PagingState<Int, NewsDataModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }


}