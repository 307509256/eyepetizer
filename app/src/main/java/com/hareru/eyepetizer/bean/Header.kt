package com.hareru.eyepetizer.bean

import kotlinx.serialization.Serializable

@Serializable
data class Header(
        val title: String = "",
        val icon: String = "",
        val description: String = "",
        val actionUrl: String = "",
)