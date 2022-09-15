package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val emptyPost = Post(
    id = 0,
    author = "Somebody",
    authorAvatar = "",
    published = 0,
    content = "",
    video = null,
    liked = false,
    likeNum = 0,
    shareNum = 0,
    viewNum = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    /*
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(application).postDao())
    private val edited = MutableLiveData(emptyPost)
    val data = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

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
    */

    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(emptyPost)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            //Начинаем загрузку
            _data.postValue(FeedModel(loading = true, refreshing = true))
            try {
                //Данные успешно получены
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                //Получена ошибка
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun saveContent() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = emptyPost
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

    fun likeById(id: Long) {    //TODO проработать тщательно
        thread {
            _data.postValue(FeedModel(loading = true))
            val oldPost = _data.value?.posts.orEmpty().find { it.id == id }
            try {
                if (oldPost != null) {
                    if (!oldPost.liked) {
                        val newPost = repository.likeById(id)
                    } else {
                        repository.unlikeById(id)
                    }
                }
                loadPosts()
                _data.postValue(FeedModel(loading = false))
            } catch (e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        }
    }

    fun unlikeById(id: Long) {
        thread { repository.unlikeById(id) }
    }

    fun shareById(id: Long) {
        thread { repository.shareById(id) }
    }

    fun removeById(id: Long) {
        thread {
            //Оптимистичная модель
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }
}