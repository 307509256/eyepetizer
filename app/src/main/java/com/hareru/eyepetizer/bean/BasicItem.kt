package com.hareru.eyepetizer.bean

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class BasicItem(
        val type: String,
        val data: JsonObject,
        val trackingData: JsonObject? = null,
        val tag: JsonObject? = null,
        val id: Int = 0,
        val adIndex: Int = -1,
)
