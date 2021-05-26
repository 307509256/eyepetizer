package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.FollowCardModel
import com.hareru.eyepetizer.databinding.ItemFollowCardBinding
import com.hareru.eyepetizer.databinding.ItemVideoBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.ui.videodetail.VideoDetailActivity
import kotlinx.serialization.json.*
import java.text.SimpleDateFormat
import java.util.*

class FollowCardDelegate : IDelegate {
    private class ViewHolder(val binding: ItemFollowCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "followCard" && item.data["dataType"]?.jsonPrimitive?.content == "FollowCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemFollowCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val model = json.decodeFromJsonElement<FollowCardModel>(item.data)
        val model2 = json.decodeFromJsonElement<VideoBeanForClient>(model.content.data)

        val binding = (holder as ViewHolder).binding
        val binding2 = ItemVideoBinding.inflate(LayoutInflater.from(binding.root.context))
        binding.flVideo.addView(binding2.root)
        binding.tvTitle.text = model.header.title
        binding.tvDescription.text = model.header.description
        binding.ivIcon.load(model.header.icon) {
            transformations(CircleCropTransformation())
        }
        binding.ivShare.setOnClickListener {
            // TODO 分享
        }
        binding.root.setOnClickListener {
            //TODO 作者信息
        }
        binding2.tvDuration.visibility = View.VISIBLE
        binding2.tvDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault(Locale.Category.FORMAT)).format(Date(model2.duration * 1000L))
        binding2.image.load(model2.cover.feed) {
            transformations(RoundedCornersTransformation(10f))
        }
        if (model2.library == "DAILY") {
            binding2.ivChoiceness.visibility = View.VISIBLE
        } else {
            binding2.ivChoiceness.visibility = View.INVISIBLE
        }
        binding2.root.setOnClickListener {
            VideoDetailActivity.start(binding.root.context, model2)
        }


    }
}



