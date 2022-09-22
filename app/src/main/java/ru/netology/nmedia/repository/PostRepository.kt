package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
//    fun likeById(id: Long)
    fun likeByIdAsync(id: Long, callback: Callback<Post>)
    fun unlikeByIdAsync(id: Long, callback: Callback<Post>)
    fun save(post: Post)
    fun removeById(id: Long)
    fun shareById(id: Long)

    fun getAllAsync(callback: Callback<List<Post>>)

    interface Callback<T> {
        fun onSuccess(posts: T)
        fun onError(e: Exception)
    }
}