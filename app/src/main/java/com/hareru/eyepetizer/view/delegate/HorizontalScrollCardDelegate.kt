package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.BasicResponse
import com.hareru.eyepetizer.databinding.ItemHorizontalScrollCardBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.ui.refresh.RefreshListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

@Deprecated("...")
class HorizontalScrollCardDelegate : IDelegate {
    private val refreshListAdapter = RefreshListAdapter()
    private val snapHelper = PagerSnapHelper()
    private val job = Job()
    private val scope = CoroutineScope(job)

    private class ViewHolder(val binding: ItemHorizontalScrollCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "horizontalScrollCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemHorizontalScrollCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<BasicResponse>(item.data)
        binding.rv.adapter = refreshListAdapter
        binding.rv.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(binding.rv)
        scope.launch {
            refreshListAdapter.submitData(PagingData.from(model.itemList))
        }
    }
}


