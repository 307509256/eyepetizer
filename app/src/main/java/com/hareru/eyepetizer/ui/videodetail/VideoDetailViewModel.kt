package com.hareru.eyepetizer.ui.videodetail

import androidx.lifecycle.*
import androidx.paging.*
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.ui.refresh.RefreshListPageDataSource
import com.hareru.eyepetizer.ui.refresh.RefreshListRepository
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class VideoDetailViewModel : ViewModel() {
    private val _videoInfo = MutableLiveData<VideoBeanForClient>()
    val videoInfo = _videoInfo.map {
        val data = json.encodeToJsonElement(it).jsonObject
        val basicItem = BasicItem(type = "videoInfo", data = data)
        PagingData.from(listOf(basicItem))
    }

    val related = _videoInfo.switchMap {
        Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
                pagingSourceFactory = { RefreshListPageDataSource(RefreshListRepository()) },
                initialKey = "http://baobab.kaiyanapp.com/api/v4/video/related?id=${it.id}"
        ).liveData
    }

    val replies = _videoInfo.switchMap {
        Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
                pagingSourceFactory = { RefreshListPageDataSource(RefreshListRepository()) },
                initialKey = "http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=${it.id}"
        ).liveData
    }

    fun setVideoInfo(videoInfo: VideoBeanForClient) {
        _videoInfo.value = videoInfo
    }
}