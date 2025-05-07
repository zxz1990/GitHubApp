package com.mcdull.githubapp.ui.home

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

class RepoAdapter : ListAdapter<Repository, RepoAdapter.ViewHolder>(RepoDiffCallback()) {
    // 添加点击监听接口
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.repoName)
        val description: TextView = itemView.findViewById(R.id.repoDescription)
        val stars: TextView = itemView.findViewById(R.id.repoStars)
        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
            // 添加按压效果
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.selector_item_bg
            )
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
            holder.stars.text = repo.stars.toString()
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