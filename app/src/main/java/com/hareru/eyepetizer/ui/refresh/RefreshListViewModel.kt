package com.hareru.eyepetizer.ui.refresh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData

class RefreshListViewModel : ViewModel() {
    private var _initialKey = MutableLiveData<String>()

    var data = _initialKey.switchMap {
        Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20),
                pagingSourceFactory = { RefreshListPageDataSource(RefreshListRepository()) },
                initialKey = it
        ).liveData
    }

    fun setInitialKey(initialKey: String) {
        _initialKey.value = initialKey
    }

    fun refresh() {
        _initialKey.value = _initialKey.value
    }

}


