package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.bean.FollowCardModel
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.databinding.ItemAutoPlayFollowCardBinding
import com.hareru.eyepetizer.remote.json
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.serialization.json.decodeFromJsonElement
import java.text.SimpleDateFormat
import java.util.*
import com.shuyu.gsyvideoplayer.GSYVideoManager

import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack


class AutoPlayFollowCardDelegate : IDelegate {
    private class ViewHolder(val binding: ItemAutoPlayFollowCardBinding) : RecyclerView.ViewHolder(binding.root)

    val gsyVideoOptionBuilder = GSYVideoOptionBuilder()

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "autoPlayFollowCard"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemAutoPlayFollowCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<FollowCardModel>(item.data)
        val model2 = json.decodeFromJsonElement<VideoBeanForClient>(model.content.data)

        binding.ivAvatar.load(model2.author.icon) {
            transformations(CircleCropTransformation())
        }
        binding.tvNickname.text = model2.author.name
        binding.tvReleaseTime.text = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault(Locale.Category.FORMAT)).format(Date(model2.releaseTime))
        binding.tvTitle.text = model2.title
        binding.tvContent.text = model2.description
        binding.tvVideoDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault(Locale.Category.FORMAT)).format(Date(model2.duration * 1000L))
        val thumbImageView = ImageView(binding.root.context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            load(model2.cover.detail)
        }
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(thumbImageView)
                .setUrl(model2.playUrl)
                .setVideoTitle(model2.title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag("${model2.id}")
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
//                .setPlayPosition(position)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        if (!binding.videoPlayer.isIfCurrentIsFullscreen()) {
                            GSYVideoManager.instance().isNeedMute = true
                        }
                    }

                    override fun onQuitFullscreen(url: String, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        GSYVideoManager.instance().isNeedMute = true
                    }

                    override fun onEnterFullscreen(url: String, vararg objects: Any) {
                        super.onEnterFullscreen(url, *objects)
                        GSYVideoManager.instance().isNeedMute = false
                        binding.videoPlayer.currentPlayer.titleTextView.text = objects[0] as String
                    }
                }).build(binding.videoPlayer)

    }
}
