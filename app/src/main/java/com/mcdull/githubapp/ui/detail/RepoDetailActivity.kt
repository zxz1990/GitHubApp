package com.mcdull.githubapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mcdull.githubapp.R
import com.mcdull.githubapp.databinding.ActivityRepoDetailBinding
import com.mcdull.githubapp.model.ContentResponse
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepoDetailBinding
    private val viewModel: RepoDetailViewModel by viewModels()

    private lateinit var adapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = intent.getParcelableExtra<Repository>("repo_data")?:run {
            finish()
            return
        }
        repo?.let {
            setupObservers()
            viewModel.loadRepoContent(
                owner = it.owner.login,  // 使用owner的login字段
                repo = it.name,
                path = ""  // 根据实际需求设置path参数
            )
        }
        adapter = ContentAdapter { content ->
            if (content.type == "dir") {
                viewModel.loadRepoContent(repo.owner.login, repo.name, content.path)
            }
        }
        binding.rvContents.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.repoContent.observe(this) { result ->
            when (result) {
//                is Result.Loading -> showLoading()
                is Result.Success -> showContent(result.data)
//                is Result.Error -> showError(result.message)
                else -> {}
            }
        }
    }

    private fun showContent(contents: List<ContentResponse>?) {
        contents?.let {
            adapter.submitList(it)
        }
    }
//    private fun showContent(content: List<ContentResponse>?) {
//        content?.let {
//            with(binding) {
//                tvContentPath.text = it.path // 确保这里使用正确的视图ID
//                tvContent.text = it.decodedContent ?: "无法解码内容"
//
//                // 根据文件类型设置样式
//                if (it.type == "file") {
//                    tvContent.setTextColor(
//                        ContextCompat.getColor(
//                            this@RepoDetailActivity,
//                            R.color.black
//                        )
//                    )
//                } else {
//                    tvContent.setTextColor(
//                        ContextCompat.getColor(
//                            this@RepoDetailActivity,
//                            com.google.android.material.R.color.material_grey_100
//                        )
//                    )
//                    tvContent.text = "目录内容请点击查看"
//                }
//            }
//        }
//    }
}