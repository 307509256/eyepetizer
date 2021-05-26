package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.databinding.ItemVideoSmallCardBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.ui.videodetail.VideoDetailActivity
import kotlinx.serialization.json.decodeFromJsonElement
import java.text.SimpleDateFormat
import java.util.*

class VideoSmallCardDelegate : IDelegate {
    private class ViewHolder(val binding: ItemVideoSmallCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "videoSmallCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemVideoSmallCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<VideoBeanForClient>(item.data)
        binding.ivPicture.load(model.cover.feed) {
            transformations(RoundedCornersTransformation(10f))
        }
        binding.tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault(Locale.Category.FORMAT)).format(Date(model.duration * 1000L))
        binding.tvTitle.text = model.title
        binding.tvDescription.text = model.description
        binding.root.setOnClickListener {
            VideoDetailActivity.start(binding.root.context, model)
        }
    }
}
