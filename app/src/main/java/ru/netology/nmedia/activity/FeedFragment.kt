package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostEventListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_feed)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        val viewModel: PostViewModel by viewModels()

//        val newPostContract = registerForActivityResult(NewPostActivityContract()) { text ->
//            text?.let {
//                viewModel.changeContent(it)
//                viewModel.saveContent()
//            }
//        }
//
//        val editPostActivityContract =
//            registerForActivityResult(EditPostActivityContract()) { text ->
//                text?.let {
//                    viewModel.changeContent(it)
//                    viewModel.saveContent()
//                }
//            }

        val adapter = PostAdapter(
            object : PostEventListener {

                override fun onEdit(post: Post) {
                    viewModel.editContent(post)
//                    binding.group.visibility = View.VISIBLE
//                    editPostActivityContract.launch(post.content)
                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
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

                override fun onVideo(post: Post) {
                    val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    startActivity(intentVideo)
                }

                override fun onPost(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_postFragment,
                        Bundle().apply {
                            idArg = post.id.toInt()
                        }
                    )
                }
            }
        )

        binding.container.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner)
        { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) binding.container.smoothScrollToPosition(0)
            }
        }

        binding.addPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
//            newPostContract.launch()
        }
        return binding.root
    }

    companion object {
        var Bundle.idArg: Int by IntArg
    }

    object IntArg : ReadWriteProperty<Bundle, Int> {
        override fun getValue(thisRef: Bundle, property: KProperty<*>): Int {
            return thisRef.getInt(property.name)
        }

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int) {
            thisRef.putInt(property.name, value)
        }
    }
}