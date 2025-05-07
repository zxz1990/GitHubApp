package com.mcdull.githubapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mcdull.githubapp.databinding.FragmentSearchBinding
import com.mcdull.githubapp.ui.home.RepoAdapter
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 初始化RecyclerView
        adapter = RepoAdapter()
        binding.rvSearchResults.adapter = adapter
        
        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString()
            val language = "Kotlin" // 可改为从UI选择
            viewModel.searchRepositories(query, language)
        }
    
        viewModel.searchResults.observe(viewLifecycleOwner) { repos ->
            repos?.let { adapter.submitList(it) }
        }
    
        viewModel.loadingState.observe(viewLifecycleOwner) { state ->
            when(state) {
                LoadingState.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvSearchResults.visibility = View.GONE
                }
                LoadingState.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvSearchResults.visibility = View.VISIBLE
                }
                is LoadingState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    // 显示错误提示...
                }
            }
        }
    }
}