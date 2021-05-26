package com.hareru.eyepetizer.bean

import kotlinx.serialization.Serializable

@Serializable
data class BasicResponse(
        val itemList: List<BasicItem>,
        val count: Int,
        val total: Int = 0,
        val nextPageUrl: String? = null,
        val adExist: Boolean = false,
)
