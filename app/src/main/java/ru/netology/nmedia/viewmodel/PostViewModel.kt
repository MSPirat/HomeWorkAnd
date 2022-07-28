package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl

val emptyPost = Post(
    0,
    "Somebody",
    "",
    "Once upon a time",
    "",
    null,
    false,
    0,
    0,
    0
)

//class PostViewModel : ViewModel() {
//    private val repository: PostRepository = PostRepositoryFileImpl()
class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.get()

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    private val edited = MutableLiveData(emptyPost)

    fun saveContent() {
        edited.value?.let {
            repository.save(it)
            edited.value = emptyPost
        }
    }

    fun editContent(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim()
            if (trimmed == it.content) {
                return
            }
            edited.value = it.copy(content = trimmed)
        }
    }
}