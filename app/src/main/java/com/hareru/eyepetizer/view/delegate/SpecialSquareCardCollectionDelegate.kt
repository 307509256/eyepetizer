package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.databinding.ItemSpecialSquareCardCollectionBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.bean.ItemCollection
import com.hareru.eyepetizer.ui.refresh.RefreshListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement

@Deprecated("...")
class SpecialSquareCardCollectionDelegate : IDelegate {
    private val refreshListAdapter = RefreshListAdapter()
    private val job = Job()
    private val scope = CoroutineScope(job)

    private class ViewHolder(val binding: ItemSpecialSquareCardCollectionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "specialSquareCardCollection"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemSpecialSquareCardCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<ItemCollection>(item.data)
        binding.header.tvText.text = model.header.title
        binding.header.tvRightText.text = model.header.rightText
        binding.header.tvRightText.setOnClickListener {
            model.header.actionUrl
        }

        binding.rv.adapter = refreshListAdapter
        binding.rv.layoutManager = GridLayoutManager(binding.root.context, 2, RecyclerView.HORIZONTAL, false)
        scope.launch {
            refreshListAdapter.submitData(PagingData.from(model.itemList))
        }
    }
}
