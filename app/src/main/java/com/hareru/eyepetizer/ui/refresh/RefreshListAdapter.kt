package com.hareru.eyepetizer.ui.refresh

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.view.delegate.*


class RefreshListAdapter : PagingDataAdapter<BasicItem, RecyclerView.ViewHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<BasicItem>() {
        override fun areItemsTheSame(oldItem: BasicItem, newItem: BasicItem): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: BasicItem, newItem: BasicItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private var delegateAdapters = IDelegate.defaultDelegate

    //代理模式
    fun addDelegate(delegate: IDelegate) {
        delegateAdapters.add(delegateAdapters.size - 1, delegate)
    }

    fun addDelegate(vararg delegate: IDelegate) {
        delegate.forEach(this::addDelegate)
    }

    fun setDelegate(delegate: MutableList<IDelegate>) {
        delegateAdapters = delegate
    }

    override fun getItemViewType(position: Int): Int {
        val indexOfFirst = delegateAdapters.indexOfFirst { it.isForViewType(getItem(position)!!) }
        return indexOfFirst
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateAdapter = delegateAdapters[viewType]
        return delegateAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegateAdapter = delegateAdapters[holder.itemViewType]
        delegateAdapter.onBindViewHolder(holder, getItem(position)!!)
    }

}


