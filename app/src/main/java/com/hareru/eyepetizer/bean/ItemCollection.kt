package com.hareru.eyepetizer.bean


import kotlinx.serialization.Serializable

@Serializable
data class ItemCollection(
        val dataType: String = "",
        val header: Header = Header(),
        val itemList: List<BasicItem> = emptyList(),
        val count: Int = 0,
) {
    @Serializable
    data class Header(
                       val id: Int = 0,
                       val title: String = "",
                       val actionUrl: String = "",
                       val rightText: String = "",
    )
}