package com.hareru.eyepetizer.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.hareru.eyepetizer.ui.refresh.RefreshListPageDataSource
import com.hareru.eyepetizer.ui.refresh.RefreshListRepository

class SearchViewModel : ViewModel() {
    private val _key = MutableLiveData<String>()
    fun setKey(key: String) {
        _key.value = key
    }

    val data = _key.switchMap {
        Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
                pagingSourceFactory = { RefreshListPageDataSource(RefreshListRepository()) },
                initialKey = "http://baobab.kaiyanapp.com/api/v3/search?query=${it}"
        ).liveData
    }
}
