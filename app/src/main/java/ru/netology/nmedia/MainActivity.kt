package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.dto.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        )
//        val like_num = findViewById<TextView>(R.id.like_num)
//        val share_num = findViewById<TextView>(R.id.share_num)

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setOnClickListener {
                post.liked = !post.liked
                like.setImageResource(
                    if (post.liked) {
                        R.drawable.ic_baseline_liked
                    } else {
                        R.drawable.ic_baseline_like
                    }
                )
                if (post.liked) {
                    post.likeNum++
                } else {
                    post.likeNum--
                }
                likeNum.text = post.likeNum.toString()
            }
            share.setOnClickListener {
                post.shareNum++
                shareNum.text = CountLikeShare.counter(post.shareNum)
//                shareNum.text = post.shareNum.toString()
                println("changed")
            }
        }
    }
}