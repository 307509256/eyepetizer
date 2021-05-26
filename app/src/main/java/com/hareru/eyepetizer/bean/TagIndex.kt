package com.hareru.eyepetizer.bean


import kotlinx.serialization.Serializable

@Serializable
data class TagIndex(
        val tabInfo: TabInfo = TabInfo(),
        val tagInfo: TagInfo = TagInfo(),
) {
    @Serializable
    data class TabInfo(
            val tabList: List<Tab> = listOf(),
            val defaultIdx: Int = 0,
    ) {
        @Serializable
        data class Tab(
                val id: Int = 0,
                val name: String = "",
                val apiUrl: String = "",
                val tabType: Int = 0,
                val nameType: Int = 0,
        )
    }

    @Serializable
    data class TagInfo(
            val dataType: String = "",
            val id: Int = 0,
            val name: String = "",
            val description: String = "",
            val headerImage: String = "",
            val bgPicture: String = "",
            val actionUrl: String? = null,
            val recType: Int = 0,
            val follow: Follow = Follow(),
            val tagFollowCount: Int = 0,
            val tagVideoCount: Int = 0,
            val tagDynamicCount: Int = 0,
            val lookCount: Int = 0,
            val shareLinkUrl: String = "",
    ) {
        @Serializable
        data class Follow(
                val itemType: String = "",
                val itemId: Int = 0,
                val followed: Boolean = false,
        )
    }
}