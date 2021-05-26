package com.hareru.eyepetizer.view.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.bean.BasicItem


interface IDelegate {
    // 查找委托时调用的方法，返回自己能处理的类型即可。
    fun isForViewType(item: BasicItem): Boolean

    // 用于委托Adapter的onCreateViewHolder方法
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    // 用于委托Adapter的onBindViewHolder方法
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem)

    companion object {
        val defaultDelegate = mutableListOf(
                AutoPlayFollowCardDelegate(),
                BannerDelegate(),
                FollowCardDelegate(),
                ReplyDelegate(),
                SquareCardOfCategoryDelegate(),
                SquareCardOfColumnDelegate(),
                TagBriefCardDelegate(),
                TextCardDelegate(),
                VideoInfoDelegate(),
                VideoSmallCardDelegate(),
                OhterDelegate()
        )
    }
}



