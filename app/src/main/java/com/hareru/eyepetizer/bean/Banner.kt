package com.hareru.eyepetizer.bean


import kotlinx.serialization.Serializable

@Serializable
data class Banner(
        val dataType: String = "",
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val image: String = "",
        val actionUrl: String = "",
        val shade: Boolean = false,
        val autoPlay: Boolean = false,
)