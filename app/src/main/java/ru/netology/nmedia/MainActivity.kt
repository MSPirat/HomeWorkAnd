package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeNum.text = CountLikeShare.counterDecimal(post.likeNum)
                shareNum.text = CountLikeShare.counterDecimal(post.shareNum)

                like.setImageResource(
                    if (post.liked) {
                        R.drawable.ic_baseline_liked
                    } else {
                        R.drawable.ic_baseline_like
                    }
                )
                like.setOnClickListener { viewModel.like() }
                share.setOnClickListener { viewModel.share() }
            }
        }
    }
}
