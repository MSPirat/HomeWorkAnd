package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    //    fun getAll(): LiveData<List<Post>>
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun unlikeById(id: Long): Post
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}