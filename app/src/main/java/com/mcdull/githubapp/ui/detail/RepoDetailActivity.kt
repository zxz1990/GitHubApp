package com.mcdull.githubapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcdull.githubapp.databinding.ActivityRepoDetailBinding
import com.mcdull.githubapp.model.Repository

class RepoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = intent.getParcelableExtra<Repository>("repo_data")
        repo?.let { bindData(it) }
    }

    private fun bindData(repo: Repository) {
        with(binding) {
            tvRepoName.text = repo.name
            tvDescription.text = repo.description
            tvStars.text = repo.stars.toString()
            tvForks.text = repo.forks.toString()
            tvLanguage.text = repo.language
            tvUpdatedAt.text = "最后更新: ${repo.updatedAt}"
        }
    }
}