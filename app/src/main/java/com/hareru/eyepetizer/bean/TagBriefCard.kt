package com.hareru.eyepetizer.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagBriefCard(
        val dataType: String = "",
        val id: Int = 0,
        val icon: String = "",
        val iconType: String = "",
        val title: String = "",
        val description: String = "",
        val actionUrl: String = "",
        val follow: Follow = Follow(),
        val ifPgc: Boolean = false,
        val ifShowNotificationIcon: Boolean = false,
        val switchStatus: Boolean = false,
        val medalIcon: Boolean = false,
        val haveReward: Boolean = false,
        val ifNewest: Boolean = false,
        val expert: Boolean = false,
) {
    @Serializable
    data class Follow(
            val itemType: String = "",
            val itemId: Int = 0,
            val followed: Boolean = false,
    )
}