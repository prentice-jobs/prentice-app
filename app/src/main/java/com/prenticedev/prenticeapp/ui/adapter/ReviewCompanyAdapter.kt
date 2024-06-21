package com.prenticedev.prenticeapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.data.remote.response.deployed.FeedResponseItems
import com.prenticedev.prenticeapp.databinding.RvItemCompanyReviewBinding
import com.prenticedev.prenticeapp.ui.activity.DetailReviewActivity

class ReviewCompanyAdapter : ListAdapter<FeedResponseItems, ReviewCompanyAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private val binding: RvItemCompanyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedResponseItems) {
//            binding.tvUser.text = item.authorId
            binding.tvReviewTitle.text = item.title
            binding.tvReviewerRole.text = item.role
            binding.tvReviewContent.text = item.description
            binding.tvUserLoc.text = item.location
            binding.tvTimeAdded.text = item.createdAt
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailReviewActivity::class.java).apply {
                        putExtra(DetailReviewActivity.EXTRA_ID, item.id)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedResponseItems>() {
            override fun areContentsTheSame(
                oldItem: FeedResponseItems,
                newItem: FeedResponseItems
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: FeedResponseItems,
                newItem: FeedResponseItems
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}