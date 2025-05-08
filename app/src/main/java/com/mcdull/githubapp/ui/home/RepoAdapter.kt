package com.mcdull.githubapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcdull.githubapp.R
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.ui.detail.RepoDetailActivity

class RepoAdapter : ListAdapter<Repository, RepoAdapter.ViewHolder>(RepoDiffCallback()) {
    // 添加点击监听接口
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.repoName)
        val description: TextView = itemView.findViewById(R.id.repoDescription)
        val author: TextView = itemView.findViewById(R.id.repoAuthor)
        val stars: TextView = itemView.findViewById(R.id.repoStars)
        val language: TextView = itemView.findViewById(R.id.repoLanguage)

        init {
            itemView.setOnClickListener {
                val repo = getItem(adapterPosition)
                repo?.let {
                    val context = itemView.context
                    val intent = Intent(context, RepoDetailActivity::class.java).apply {
                        putExtra("repo_data", repo) // 需要让Repository实现Parcelable
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { repo ->
            holder.name.text = repo.name
            holder.description.text = repo.description ?: ""
            holder.author.text = holder.author.context.getString(R.string.author, repo.owner.login)
            holder.stars.text = repo.stars.toString()
            holder.language.text = repo.language ?: "N/A"
        }
    }

    class RepoDiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}