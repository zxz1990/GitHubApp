package com.mcdull.githubapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mcdull.githubapp.R
import com.mcdull.githubapp.databinding.ActivityRepoDetailBinding
import com.mcdull.githubapp.model.ContentResponse
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.model.Result
import com.mcdull.githubapp.ui.issue.CreateIssueActivity
import com.mcdull.githubapp.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepoDetailBinding
    private val viewModel: RepoDetailViewModel by viewModels()

    private lateinit var adapter: ContentAdapter

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repo = intent.getParcelableExtra<Repository>("repo_data") ?: run {
            finish()
            return
        }

        with(binding) {
            tvRepoName.text = repo.name
            tvDescription.text = repo.description ?: "无描述"
            tvStars.text = "${repo.stars}"
            tvForks.text = "${repo.forks}"
            tvLanguage.text = repo.language ?: "未知语言"
            tvUpdatedAt.text = "最后更新：${repo.updatedAt}"
        }

        repo?.let {
            setupObservers()
            viewModel.loadRepoContent(
                owner = it.owner.login,  // 使用owner的login字段
                repo = it.name,
                path = ""  // 根据实际需求设置path参数
            )
        }
        userManager.accessToken.observe(this) { token ->
            if (token != null) {
                binding.fabSubmitIssue.apply {
                    show()
                    setOnClickListener {
                        startActivity(
                            Intent(
                                this@RepoDetailActivity,
                                CreateIssueActivity::class.java
                            ).apply {
                                putExtra("owner", repo.owner.login)
                                putExtra("repo", repo.name)
                            })
                    }
                }
            } else {
            }
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