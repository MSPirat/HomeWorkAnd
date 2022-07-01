package ru.netology.dto

data class Post(
    val id: Long = 0,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    var liked: Boolean = false,

    var likeNum: Long = 999,
    var shareNum: Long = 1299995
)