package com.edit.chatnex.repository


import com.edit.chatnex.model.ArticleDetailModel
import com.edit.chatnex.model.LoginDataModel
import com.edit.chatnex.model.NewsListDataModel
import com.edit.chatnex.model.ClsListDataModel
import com.google.gson.JsonElement
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/auth/editapplogin")
    suspend fun submitMessage(
        @Body loginDataModel: LoginDataModel
    ): JsonElement

    @GET("cms/api/hotspot/get/news/hot/spot/list?category=&key_word=&app_name=&important=0&time=0&last_id=&api_v=1.4.8&mp_v=1.4.8")
    suspend fun getNewsList(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("last_id") lastId: String
    ):ApiResponse<NewsListDataModel>


    @GET
    suspend fun getClsList(
        @Url apiUrl: String,
        @Query("last_time") lastTime: Long,
        @Query("sign") sign: String = "52ba76e80184028c9c4a1e320832b897",
        @Query("sv") sv: Int = 1,
        @Query("app") app: String = "CailianpressWap",
        @Query("refresh_type") refreshType: Int = 1,
        @Query("rn") pageSize: Int = 10
    ): NewsApiResponse<ClsListDataModel>

    @GET
    suspend fun getArticleDetail(
        @Url apiUrl: String,
        @Query("id") id: Int,
        @Query("sign") sign: String = "52ba76e80184028c9c4a1e320832b897",
        @Query("sv") sv: Int = 1,
        @Query("app") app: String = "CailianpressWap",
    ): NewsApiResponse<ArticleDetailModel>


}