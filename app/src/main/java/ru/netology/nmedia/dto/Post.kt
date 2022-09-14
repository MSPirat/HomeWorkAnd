package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    val video: String? = null,
    val liked: Boolean,

    val likeNum: Long = 0,
    val shareNum: Long = 0,
    val viewNum: Long = 0
)