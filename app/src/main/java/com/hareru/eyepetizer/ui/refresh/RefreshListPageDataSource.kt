package com.hareru.eyepetizer.ui.refresh

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hareru.eyepetizer.bean.BasicItem


class RefreshListPageDataSource(private val repository: RefreshListRepository) : PagingSource<String, BasicItem>() {
    override fun getRefreshKey(state: PagingState<String, BasicItem>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, BasicItem> {
        try {
            val response = repository.basicGet(params.key!!)
            return LoadResult.Page(response.itemList, null, response.nextPageUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }


}


