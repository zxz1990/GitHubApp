package com.mcdull.githubapp.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcdull.githubapp.databinding.FragmentTrendingTopicsBinding

class TrendingTopicsFragment : Fragment() {
    private var _binding: FragmentTrendingTopicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }
}