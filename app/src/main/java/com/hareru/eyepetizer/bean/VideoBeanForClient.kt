package com.hareru.eyepetizer.bean


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class VideoBeanForClient(
        val dataType: String = "",
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val library: String = "",
        val tags: List<Tag> = listOf(),
        val consumption: Consumption = Consumption(),
        val resourceType: String = "",
        val provider: Provider = Provider(),
        val category: String = "",
        val author: Author = Author(),
        val cover: Cover = Cover(),
        val playUrl: String = "",
        val duration: Long = 0,
        val webUrl: WebUrl = WebUrl(),
        val releaseTime: Long = 0,
        val playInfo: List<PlayInfo> = listOf(),
        val type: String = "",
        val remark: String = "",
        val ifLimitVideo: Boolean = false,
        val searchWeight: Int = 0,
        val idx: Int = 0,
        val date: Long = 0,
        val descriptionEditor: String = "",
        val collected: Boolean = false,
        val reallyCollected: Boolean = false,
        val played: Boolean = false,
) : Parcelable {
    @Parcelize
    @Serializable
    data class Tag(
            val id: Int = 0,
            val name: String = "",
            val actionUrl: String = "",
            val desc: String = "",
            val bgPicture: String = "",
            val headerImage: String = "",
            val tagRecType: String = "",
            val haveReward: Boolean = false,
            val ifNewest: Boolean = false,
            val communityIndex: Int = 0,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Consumption(
            val collectionCount: Int = 0,
            val shareCount: Int = 0,
            val replyCount: Int = 0,
            val realCollectionCount: Int = 0,
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Provider(
            val name: String = "",
            val alias: String = "",
            val icon: String = "",
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Author(
            val id: Int = 0,
            val icon: String = "",
            val name: String = "",
            val description: String = "",
            val link: String = "",
            val latestReleaseTime: Long = 0,
            val videoNum: Int = 0,
            val follow: Follow = Follow(),
            val shield: Shield = Shield(),
            val approvedNotReadyVideoCount: Int = 0,
            val ifPgc: Boolean = false,
            val recSort: Int = 0,
            val expert: Boolean = false,
    ) : Parcelable {
        @Parcelize
        @Serializable
        data class Follow(
                val itemType: String = "",
                val itemId: Int = 0,
                val followed: Boolean = false,
        ) : Parcelable

        @Parcelize
        @Serializable
        data class Shield(
                val itemType: String = "",
                val itemId: Int = 0,
                val shielded: Boolean = false,
        ) : Parcelable
    }

    @Parcelize
    @Serializable
    data class Cover(
            val feed: String = "",
            val detail: String = "",
            val blurred: String = "",
            val homepage: String = "",
    ) : Parcelable

    @Parcelize
    @Serializable
    data class WebUrl(
            val raw: String = "",
            val forWeibo: String = "",
    ) : Parcelable

    @Parcelize
    @Serializable
    data class PlayInfo(
            val height: Int = 0,
            val width: Int = 0,
            val urlList: List<Url> = listOf(),
            val name: String = "",
            val type: String = "",
            val url: String = "",
    ) : Parcelable {
        @Parcelize
        @Serializable
        data class Url(
                val name: String = "",
                val url: String = "",
                val size: Long = 0,
        ) : Parcelable
    }

}