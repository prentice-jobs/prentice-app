package com.prenticedev.prenticeapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.data.remote.response.deployed.DetailCompanyReviewItems
import com.prenticedev.prenticeapp.databinding.RvItemCompanyDetailBinding
import com.prenticedev.prenticeapp.ui.activity.DetailReviewActivity

class DetailCompanyAdapter :
    ListAdapter<DetailCompanyReviewItems, DetailCompanyAdapter.MyViewHolder>(
        DIFF_CALLBACK
    ) {
    class MyViewHolder(private val binding: RvItemCompanyDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailCompanyReviewItems) {
            binding.tvReviewTitle.text = item.title
            binding.tvReviewContent.text = item.description
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
            RvItemCompanyDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userReviews = getItem(position)
        holder.bind(userReviews)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailCompanyReviewItems>() {
            override fun areContentsTheSame(
                oldItem: DetailCompanyReviewItems,
                newItem: DetailCompanyReviewItems
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: DetailCompanyReviewItems,
                newItem: DetailCompanyReviewItems
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}