package com.hareru.eyepetizer.remote

import com.hareru.eyepetizer.bean.BasicResponse
import com.hareru.eyepetizer.bean.TagIndex
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface KaiYanService {
    companion object {
        const val DISCOVERY_URL = "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery"
        const val RECOMMEND_URL = "http://baobab.kaiyanapp.com/api/v5/index/tab/allRec"
        const val DAILY_URL = "http://baobab.kaiyanapp.com/api/v5/index/tab/feed"
    }

    @GET
    suspend fun basicGet(@Url url: String): BasicResponse

    @GET("/api/v6/tag/index")
    suspend fun tagIndex(@Query("id") id: String): TagIndex
}
