package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentSignInBinding
import ru.netology.nmedia.di.DependencyContainer
import ru.netology.nmedia.viewmodel.SignInViewModel
import ru.netology.nmedia.viewmodel.ViewModelFactory

class SignInFragment : Fragment() {

    private val dependencyContainer = DependencyContainer.getInstance()

    private val viewModel: SignInViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
        factoryProducer = {
            ViewModelFactory(
                dependencyContainer.repository,
                dependencyContainer.appAuth
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        viewModel.data.observe(viewLifecycleOwner) {
            dependencyContainer.appAuth.setAuth(it.id, it.token)
            findNavController().navigateUp()
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.loginError) {
                binding.password.error = getString(R.string.wrong_password)
            }
        }

        with(binding) {
            login.requestFocus()
            signInButton.setOnClickListener {
//                println("pushed button")
                viewModel.loginAttempt(
                    login.editText?.text.toString(),
                    password.editText?.text.toString()
                )
            }
        }
        return binding.root
    }
}