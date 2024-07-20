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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewCompanyAdapter : ListAdapter<FeedResponseItems, ReviewCompanyAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private val binding: RvItemCompanyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedResponseItems) {
//            binding.tvUser.text = item.authorId
            val formattedDate =formatDate(item.createdAt.toString())
            binding.tvReviewTitle.text = item.title
            binding.tvReviewerRole.text = item.role
            binding.tvReviewContent.text = item.description
            binding.tvUserLoc.text = item.location
            binding.tvTimeAdded.text = formattedDate
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailReviewActivity::class.java).apply {
                        putExtra(DetailReviewActivity.EXTRA_ID, item.id)
                        context.startActivity(this)
                    }
                }
            }

        }

        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val date: Date = inputFormat.parse(dateString) ?: Date()

                outputFormat.format(date)
            } catch (e: Exception) {
                dateString
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