package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hareru.eyepetizer.bean.Banner
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.databinding.ItemBannerBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.temp.deepLink
import kotlinx.serialization.json.decodeFromJsonElement

class BannerDelegate : IDelegate {
    private class ViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "banner"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<Banner>(item.data)
        binding.image.load(model.image) {
            transformations(RoundedCornersTransformation(10f))
        }
        binding.root.deepLink(model.actionUrl)
    }
}

