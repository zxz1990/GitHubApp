package com.mcdull.githubapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcdull.githubapp.databinding.FragmentProfileBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcdull.githubapp.R
import com.mcdull.githubapp.ui.home.RepoAdapter
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

    private lateinit var adapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupObservers()
        setupLoginButton()
        setupRecyclerView()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            Log.d(TAG, "setupObservers() called with: state = $state")
            when (state) {
                is AuthState.Success -> handleLoginSuccess(state.token)
                is AuthState.Error -> showError(state.message)
                AuthState.Loading -> {
//                    showLoading()
                }
            }
        }

        userManager.accessToken.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                showLoggedInUI()
                fetchUserInfo(token)
                binding.rvUserRepos.visibility = View.VISIBLE // 显示仓库列表
            } else {
                showLoggedOutUI()
                binding.rvUserRepos.visibility = View.GONE // 隐藏仓库列表
            }
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            userManager.clearAuth()
        }
    }

    private fun showLoggedInUI() {
        binding.btnLogin.visibility = View.GONE
        binding.btnLogout.visibility = View.VISIBLE
        binding.tvUserInfo.visibility = View.VISIBLE
    }

    private fun showLoggedOutUI() {
        binding.btnLogin.visibility = View.VISIBLE
        binding.btnLogout.visibility = View.GONE
        binding.tvUserInfo.visibility = View.GONE
    }

    private fun handleLoginSuccess(token: String) {
        // 保存token后会自动触发accessToken的观察
        binding.tvUserInfo.text = "登录成功，token: ${token.take(8)}..."
    }

    private fun setupRecyclerView() {
        adapter = RepoAdapter()
        binding.rvUserRepos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProfileFragment.adapter
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
                    setDrawable(it)
                }
            }
        }
    }

    private fun fetchUserInfo(token: String) {
        viewModel.loadUserRepositories(token)
        viewModel.repositories.observe(viewLifecycleOwner) { repos ->
            repos?.let { adapter.submitList(it) }
        }
    }

    private fun showError(message: String) {
        // ... existing error handling ...
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLogoutButton()
    }
}