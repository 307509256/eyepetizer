package com.hareru.eyepetizer.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextCard(
        val dataType: String = "",
        val id: Int = 0,
        val type: String = "",
        val text: String = "",
        val actionUrl: String = "",
        val rightText: String = "",
)