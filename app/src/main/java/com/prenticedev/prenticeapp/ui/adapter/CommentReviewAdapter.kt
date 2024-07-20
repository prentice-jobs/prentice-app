package com.prenticedev.prenticeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.data.local.model.CommentModel
import com.prenticedev.prenticeapp.databinding.RvItemReviewCommentBinding

class CommentReviewAdapter :
    ListAdapter<CommentModel, CommentReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvItemReviewCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentModel) {
            binding.tvUserComment.text = item.name
            binding.tvCommentContent.text = item.comment

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentModel>() {
            override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            RvItemReviewCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userComment = getItem(position)
        holder.bind(userComment)
    }
}