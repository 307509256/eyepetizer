package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.databinding.ItemReplyBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.bean.ReplyBeanForClient
import kotlinx.serialization.json.decodeFromJsonElement
import java.text.SimpleDateFormat
import java.util.*


class ReplyDelegate : IDelegate {
    private class ViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "reply"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<ReplyBeanForClient>(item.data)
        binding.ivAvatar.load(model.user.avatar) {
            transformations(CircleCropTransformation())
        }
        binding.tvNickName.text = model.user.nickname
        binding.tvLikeCount.text = model.likeCount.toString()
        binding.tvMessage.text = model.message
        binding.tvTime.text = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault(Locale.Category.FORMAT)).format(Date(model.createTime))
    }
}
