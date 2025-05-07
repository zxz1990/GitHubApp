package com.mcdull.githubapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcdull.githubapp.databinding.FragmentProfileBinding
import androidx.lifecycle.ViewModelProvider
import com.mcdull.githubapp.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    companion object {
        private const val TAG = "ProfileFragment"
    }

    @Inject
    lateinit var userManager: UserManager

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        setupObservers()
        setupLoginButton()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            Log.d(TAG, "setupObservers() called with: state = $state")
//            when (state) {
//                is AuthState.Success -> handleLoginSuccess(state.token)
//                is AuthState.Error -> showError(state.message)
//                AuthState.Loading -> showLoading()
//            }
        }
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            viewModel.startOAuthFlow(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}