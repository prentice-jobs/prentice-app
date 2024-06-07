package com.prenticedev.prenticeapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prenticedev.prenticeapp.data.local.model.ReviewModel
import com.prenticedev.prenticeapp.databinding.RvItemCompanyReviewBinding
import com.prenticedev.prenticeapp.ui.DetailReviewActivity

class ReviewCompanyAdapter : ListAdapter<ReviewModel, ReviewCompanyAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private val binding: RvItemCompanyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewModel) {
            Glide.with(itemView.context).load(item.userPhoto).into(binding.imUserReviewer)
            binding.tvReviewerRole.text = item.role
            binding.tvReviewerStatus.text = item.status
            binding.tvUser.text = item.userName
            binding.tvUserLoc.text = item.location
            binding.tvReviewTitle.text = item.reviewTitle
            binding.tvReviewContent.text = item.reviewContent
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailReviewActivity::class.java).apply {
                        context.startActivity(this)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            RvItemCompanyReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userReviews = getItem(position)
        holder.bind(userReviews)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}