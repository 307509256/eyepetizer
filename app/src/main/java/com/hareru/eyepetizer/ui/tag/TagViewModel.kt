package com.hareru.eyepetizer.ui.tag

import androidx.lifecycle.*
import com.hareru.eyepetizer.ui.refresh.RefreshListRepository

class TagViewModel : ViewModel() {
    private val _tagID = MutableLiveData<String>()
    private val r = RefreshListRepository()
    val d1 = _tagID.switchMap {
        liveData {
            emit(r.tagIndex(it))
        }
    }

    fun setTagID(tagId: String) {
        _tagID.value = tagId
    }

}
