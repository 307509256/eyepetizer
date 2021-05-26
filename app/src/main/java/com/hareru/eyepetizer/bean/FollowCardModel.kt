package com.hareru.eyepetizer.bean

import kotlinx.serialization.Serializable

@Serializable
data class FollowCardModel(
        val dataType: String,
        val header: Header,
        val content: BasicItem,
)
