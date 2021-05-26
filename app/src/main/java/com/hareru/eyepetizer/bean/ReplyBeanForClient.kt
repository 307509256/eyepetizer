package com.hareru.eyepetizer.bean


import kotlinx.serialization.Serializable

@Serializable
data class ReplyBeanForClient(
        val dataType: String = "",
        val id: Long = 0,
        val videoId: Long = 0,
        val videoTitle: String = "",
        val parentReplyId: Long = 0,
        val rootReplyId: Long = 0,
        val sequence: Long = 0,
        val message: String = "",
        val replyStatus: String = "",
        val createTime: Long = 0,
        val user: User = User(),
        val likeCount: Long = 0,
        val liked: Boolean = false,
        val hot: Boolean = false,
        val type: String = "",
        val showParentReply: Boolean = false,
        val showConversationButton: Boolean = false,
        val sid: String = "",
        val userBlocked: Boolean = false,
) {
    @Serializable
    data class User(
            val uid: Long = 0,
            val nickname: String = "",
            val avatar: String = "",
            val userType: String = "",
            val ifPgc: Boolean = false,
            val registDate: Long = 0,
            val releaseDate: Long = 0,
            val actionUrl: String = "",
            val followed: Boolean = false,
            val limitVideoOpen: Boolean = false,
            val library: String = "",
    )
}