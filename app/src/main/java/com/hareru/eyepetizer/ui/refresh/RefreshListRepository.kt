package com.hareru.eyepetizer.ui.refresh

import com.hareru.eyepetizer.bean.BasicResponse
import com.hareru.eyepetizer.remote.kaiYanService
import com.hareru.eyepetizer.bean.TagIndex

class RefreshListRepository {

    suspend fun basicGet(url: String): BasicResponse {
        return kaiYanService.basicGet(url)
    }

    suspend fun tagIndex(id: String): TagIndex {
        return kaiYanService.tagIndex(id)
    }
}