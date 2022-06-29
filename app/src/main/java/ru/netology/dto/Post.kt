package ru.netology.dto

data class Post(
    val id: Long = 0,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    var liked: Boolean = false,

    var likeNum: Float = 999f,
    var shareNum: Float = 1989f
)