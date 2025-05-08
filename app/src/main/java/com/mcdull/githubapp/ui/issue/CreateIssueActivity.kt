package com.mcdull.githubapp.ui.issue

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mcdull.githubapp.databinding.ActivityCreateIssueBinding
import dagger.hilt.android.AndroidEntryPoint
import com.mcdull.githubapp.model.Result

@AndroidEntryPoint
class CreateIssueActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateIssueBinding
    private val viewModel: IssueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateIssueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()
            viewModel.createIssue(
                intent.getStringExtra("owner")!!,
                intent.getStringExtra("repo")!!,
                title, body
            )
        }

        viewModel.issueResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(this, "提交问题成功", Toast.LENGTH_LONG).show()
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this, "提交问题失败", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }
}
