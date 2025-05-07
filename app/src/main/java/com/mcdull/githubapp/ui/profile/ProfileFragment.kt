package com.mcdull.githubapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcdull.githubapp.databinding.FragmentProfileBinding
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {
    companion object {
        private const val TAG = "ProfileFragment"
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
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