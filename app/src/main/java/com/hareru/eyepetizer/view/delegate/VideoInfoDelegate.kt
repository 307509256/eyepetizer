package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.databinding.ItemNewDetailCustomHeaderTypeBinding
import com.hareru.eyepetizer.remote.json
import kotlinx.serialization.json.decodeFromJsonElement

class VideoInfoDelegate : IDelegate {
    class ViewHolder(val binding: ItemNewDetailCustomHeaderTypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "videoInfo"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNewDetailCustomHeaderTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<VideoBeanForClient>(item.data)
        binding.tvTitle.text = model.title
        binding.tvCategory.text = "#${model.category}"
        binding.tvDescription.text = model.description
        binding.ivAvatar.load(model.author.icon) {
            transformations(CircleCropTransformation())
        }
        binding.tvAuthorDescription.text = model.author.description
        binding.tvAuthorName.text = model.author.name
    }
}
