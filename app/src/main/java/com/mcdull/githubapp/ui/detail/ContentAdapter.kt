package com.mcdull.githubapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcdull.githubapp.R
import com.mcdull.githubapp.databinding.ItemContentBinding
import com.mcdull.githubapp.model.ContentResponse

class ContentAdapter(
    private val onItemClick: (ContentResponse) -> Unit
) : ListAdapter<ContentResponse, ContentAdapter.ViewHolder>(ContentDiffCallback()) {

    inner class ViewHolder(private val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: ContentResponse) {
            with(binding) {
                tvName.text = content.name
                tvPath.text = content.path
                ivType.setImageResource(
                    when (content.type) {
                        "dir" -> R.drawable.ic_folder
                        else -> R.drawable.ic_file
                    }
                )
                
                root.setOnClickListener { onItemClick(content) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentDiffCallback : DiffUtil.ItemCallback<ContentResponse>() {
        override fun areItemsTheSame(oldItem: ContentResponse, newItem: ContentResponse): Boolean {
            return oldItem.sha == newItem.sha
        }

        override fun areContentsTheSame(oldItem: ContentResponse, newItem: ContentResponse): Boolean {
            return oldItem == newItem
        }
    }
}