package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.TextCard
import com.hareru.eyepetizer.databinding.ItemTextBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.temp.deepLink
import kotlinx.serialization.json.decodeFromJsonElement

class TextCardDelegate : IDelegate {
    private class ViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "textCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<TextCard>(item.data)
        binding.tvText.text = model.text
        binding.tvRightText.text = model.rightText
        if (model.actionUrl.isEmpty()) {
            binding.tvRightText.visibility = View.INVISIBLE
            binding.root.deepLink(model.actionUrl)
        }
    }
}
