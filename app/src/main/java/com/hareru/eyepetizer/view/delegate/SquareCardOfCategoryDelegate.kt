package com.hareru.eyepetizer.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hareru.eyepetizer.bean.BasicItem
import com.hareru.eyepetizer.databinding.ItemSquareCardOfCategoryBinding
import com.hareru.eyepetizer.remote.json
import com.hareru.eyepetizer.bean.SquareCard
import com.hareru.eyepetizer.temp.deepLink
import kotlinx.serialization.json.decodeFromJsonElement

class SquareCardOfCategoryDelegate : IDelegate {
    private class ViewHolder(val binding: ItemSquareCardOfCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: BasicItem): Boolean {
        return item.type == "squareCardOfCategory"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = ItemSquareCardOfCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BasicItem) {
        val binding = (holder as ViewHolder).binding
        val model = json.decodeFromJsonElement<SquareCard>(item.data)
        binding.image.load(model.image) {
            transformations(RoundedCornersTransformation(10f))

        }
        binding.title.text = model.title
        binding.root.deepLink(model.actionUrl)

    }
}
