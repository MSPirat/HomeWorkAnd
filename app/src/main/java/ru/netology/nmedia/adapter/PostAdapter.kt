package ru.netology.nmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.CountLikeShare

typealias LikeReturn = (post: Post) -> Unit
typealias ShareReturn = (post: Post) -> Unit

class PostAdapter(
    private val likeReturn: LikeReturn,
    private val shareReturn: ShareReturn
) : RecyclerView.Adapter<PostViewHolder>() {

    var list = emptyList<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeReturn, shareReturn)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeReturn: LikeReturn,
    private val shareReturn: ShareReturn
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
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
            like.setOnClickListener { likeReturn(post) }
            share.setOnClickListener { shareReturn(post) }
        }
    }
}