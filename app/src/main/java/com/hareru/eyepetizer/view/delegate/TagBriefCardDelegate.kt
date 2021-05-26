package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.TagBriefCard
import com.hareru.eyepetizer.databinding.ItemTagBriefCardBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.temp.deepLink
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

class TagBriefCardDelegate : IDelegate {
    private class ViewHolder(val binding: ItemTagBriefCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "briefCard" && item.data["dataType"]?.jsonPrimitive?.content == "TagBriefCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemTagBriefCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val model = json.decodeFromJsonElement<TagBriefCard>(item.data)
        val binding = (holder as ViewHolder).binding
        binding.ivPicture.load(model.icon) {
            transformations(RoundedCornersTransformation(10f))
        }
        binding.tvTitle.text = model.title
        binding.tvDescription.text = model.description
        binding.tvFollow
        //TODO
        binding.root.deepLink(model.actionUrl)

    }
}
