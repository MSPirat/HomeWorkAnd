package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostEventListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivityContract()) { text ->
            text?.let {
                viewModel.changeContent(it)
                viewModel.saveContent()
            }
        }

//        val viewModel: PostViewModel by viewModels()

//        binding.group.visibility = View.GONE

        val editPostActivityContract =
            registerForActivityResult(EditPostActivityContract()) { text ->
                text?.let {
                    viewModel.changeContent(it)
                    viewModel.saveContent()
                }
            }

        val adapter = PostAdapter(
            object : PostEventListener {

                override fun onEdit(post: Post) {
                    viewModel.editContent(post)
//                    binding.group.visibility = View.VISIBLE
                    editPostActivityContract.launch(post.content)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }
            }
        )

        binding.container.adapter = adapter

        viewModel.data.observe(this)
        { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) binding.container.smoothScrollToPosition(0)
            }
        }

        binding.addPost.setOnClickListener {
            newPostContract.launch()
        }


//        viewModel.edited.observe(this) {
//            with(binding.contentCheck) {
//                text = it.content
//            }
//            binding.content.setText(it.content)
//        }

//        binding.save.setOnClickListener {
//            with(binding.content) {
////                if (text.isNullOrBlank()) {
////                    Toast.makeText(
////                        this@MainActivity,
////                        context.getString(R.string.empty_post_error),
////                        Toast.LENGTH_SHORT
////                    ).show()
////                    return@setOnClickListener
////                }
//                viewModel.changeContent(text.toString())
//                viewModel.saveContent()
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
//        }

//        binding.cancel.setOnClickListener {
//            with(binding.content) {
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//        }
    }
}
