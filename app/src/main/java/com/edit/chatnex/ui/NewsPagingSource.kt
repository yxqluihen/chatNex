package com.edit.chatnex.ui

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edit.chatnex.model.LoginDataModel
import com.edit.chatnex.model.NewsDataModel
import com.edit.chatnex.model.NewsListDataModel
import com.edit.chatnex.repository.ApiResponse
import com.edit.chatnex.repository.AppRepository
import com.edit.chatnex.repository.AppUrlPath
import com.google.gson.Gson

class NewsPagingSource:
    PagingSource<Int, NewsDataModel>() {

    var token : String? = null
    var news : ApiResponse<NewsListDataModel>? = null
    var lastId = ""

    override fun getRefreshKey(state: PagingState<Int, NewsDataModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsDataModel> {
        try {
            token = getToken()
            Log.d("token+++","${token}")
            val page = params.key ?: 1 //当前页码，若为空则设为1
            lastId = getLastId(1, token!!,lastId)
            Log.d("lastId====", lastId)
            news = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page, 20,lastId)


//            val response = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page,20,lastId)
//            lastId = response.data?.list?.last()?.orderTime.toString()
            val data = news?.data?.list
            Log.d("data+++","${data}")
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data?.isEmpty() == true) null else page + 1

//            val prevKey = if (page > 1) page - 1 else null   //计算上一页的页码
//            val nextKey = if (news?.data?.list != null) page + 1 else null //计算下一页的页码
            return LoadResult.Page(
                data = data as ArrayList<NewsDataModel>,
                prevKey = prevKey,
                nextKey = nextKey,
            )}catch (e:Exception){
            return LoadResult.Error(e)
        }



//            var list: List<NewsDataModel>? = null
//            var page = params.key ?: 1  //当前页码，若为空则设为1
//            val pageSize = params.loadSize  //每次加载的数目
//            token = getToken()
//            Log.d("token+++","${token}")
//            news = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page,pageSize,lastId)
//
//            Log.d("first-NewsListDataModel=","${news?.data?.list}")
//            if (page == 1){
////                token = getToken()
//                Log.d("token===","${token}")
//                news = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page,pageSize,lastId)
//                Log.d("page-NewsListDataModel=","${news?.data?.list}")
//                lastId = news?.data?.list?.last()?.orderTime.toString()
//            }else{
//                news = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page,pageSize,lastId)
//                lastId = news?.data?.list?.last()?.orderTime.toString()
//            }
//            val prevKey = if (page > 1) page - 1 else null   //计算上一页的页码
//            val nextKey =
//                if (news?.data?.list != null) page + 1 else null //计算下一页的页码
//            //将数据和页码设置到LoadResult中
//            list = news?.data?.list
//            Log.d("NewsListDataModel=","${news?.data?.list}")
//            return LoadResult.Page(
//                data = list as ArrayList<NewsDataModel>,
//                prevKey = prevKey,
//                nextKey = nextKey,
//            )
//        }catch (e:Exception){
//            return LoadResult.Error(e)
//        }


    }
    //如果需要从中间开始加载的话，就按如下方法即可。
//    override fun getRefreshKey(state: PagingState<Int, NewsDataModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }

    private suspend fun getLastId(page: Int,token:String,id:String): String {
        val response = AppRepository.getApiService(AppUrlPath.SAAS_HOST).getNewsList("Bearer $token",page,20,id)
        lastId = response.data?.list?.last()?.orderTime.toString()
        Log.d("NewsListDataModel=","${response.data?.list}")
        Log.d("getLastId+++", response.data?.list?.last()?.orderTime.toString())
        return lastId
    }

    suspend fun getToken():String{
        val jsonStr = """
            {
            	"_brand": "iPhone,15.7.3",
            	"_mac": "02:00:00:00:00:00",
            	"_net": "wifi",
            	"_systype": "iphone",
            	"_udid": "2b9b3e39e3e90a23ee5aa8ebc457a43b",
            	"_uid": "",
            	"_v": "2.5.10",
            	"_version": "1.1",
            	"email": "xq02@saas.com",
            	"password": "4fb69d0f1c9b2eaac8ab9a6ca1d784ba"
            }
        """.trimIndent()

        val gson = Gson()
        val info = gson.fromJson(jsonStr, LoginDataModel::class.java)
        val response = AppRepository.getApiService(AppUrlPath.SAAS_HOST).submitMessage(info)

        val token = response.asJsonObject
            .getAsJsonObject("data")
            .getAsJsonObject("userinfo")
            .get("token")
        return token.asString
    }
}