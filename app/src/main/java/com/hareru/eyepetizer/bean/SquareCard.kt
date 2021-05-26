package com.hareru.eyepetizer.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SquareCard(
       val dataType: String = "",
       val id: Int = 0,
       val title: String = "",
       val image: String = "",
       val actionUrl: String = "",
       val shade: Boolean = false
)