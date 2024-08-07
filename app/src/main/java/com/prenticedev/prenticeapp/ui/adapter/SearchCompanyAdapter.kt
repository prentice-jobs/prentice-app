package com.prenticedev.prenticeapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.prenticedev.prenticeapp.data.remote.response.deployed.CompanyResponseItem
import com.prenticedev.prenticeapp.databinding.RvItemCompanyExploreBinding
import com.prenticedev.prenticeapp.ui.activity.CompanyExploreDetailActivity

class SearchCompanyAdapter() : ListAdapter<CompanyResponseItem, SearchCompanyAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private val binding: RvItemCompanyExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CompanyResponseItem) {
            Glide.with(itemView.context).load(item.logoUrl).into(binding.imCompany)
            binding.TvCompanyName.text = item.displayName
//            binding.tvCompanyCategory.text = item.tags
            val companyTags = item.tags
            binding.tvRatingValue.text = item.starRating.toString()
            binding.tvSeeReviews. text ="See ${item.reviewCount.toString()} Reviews"
            with(itemView) {
                setOnClickListener {
                    Intent(context, CompanyExploreDetailActivity::class.java).apply {
                        putExtra(CompanyExploreDetailActivity.EXTRA_ID, item.id)
                        context.startActivity(this)
                    }
                }
                if (companyTags != null) {
                    val chip = Chip(context).apply {
                        text = companyTags.toString()
                    }
                    binding.companyTag.addView(chip)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            RvItemCompanyExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val companies = getItem(position)
        holder.bind(companies)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CompanyResponseItem>() {
            override fun areContentsTheSame(
                oldItem: CompanyResponseItem,
                newItem: CompanyResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CompanyResponseItem,
                newItem: CompanyResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}