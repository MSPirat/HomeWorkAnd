package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.dto.Post

class InMemoryPostRepository : PostRepository {

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        authorAvatar = "",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        liked = false,
        likeNum = 9999,
        shareNum = 1799995
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(liked = !post.liked)
        post = if (post.liked) {
            post.copy(likeNum = post.likeNum + 1)
        } else {
            post.copy(likeNum = post.likeNum - 1)
        }
        data.value = post
    }

    override fun share() {
        post = post.copy(
            shareNum = post.shareNum + 1
        )
        data.value = post
    }
}