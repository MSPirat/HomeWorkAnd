package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.CountLikeShare

typealias onLikeListener = (post: Post) -> Unit
typealias onShareListener = (post: Post) -> Unit
typealias onRemoveListener = (post: Post) -> Unit

class PostAdapter(
    private val onLikeListener: onLikeListener,
    private val onShareListener: onShareListener,
    private val onRemoveListener: onLikeListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(
            binding = binding,
            onLikeListener = onLikeListener,
            onShareListener = onShareListener,
            onRemoveListener = onRemoveListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: onLikeListener,
    private val onShareListener: onShareListener,
    private val onRemoveListener: onLikeListener
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
            like.setOnClickListener { onLikeListener(post) }
            share.setOnClickListener { onShareListener(post) }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                onRemoveListener(post)
                                return@setOnMenuItemClickListener true
                            }
                        }
                        false
                    }
                    show()
                }
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Post, newItem: Post): Any = Unit
}