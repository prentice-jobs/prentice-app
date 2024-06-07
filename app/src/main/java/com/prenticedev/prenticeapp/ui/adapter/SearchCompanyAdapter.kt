package com.prenticedev.prenticeapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prenticedev.prenticeapp.data.local.model.CompanyModel
import com.prenticedev.prenticeapp.databinding.RvItemCompanyExploreBinding
import com.prenticedev.prenticeapp.ui.CompanyExploreDetailActivity

class SearchCompanyAdapter() : ListAdapter<CompanyModel, SearchCompanyAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    class MyViewHolder(private val binding: RvItemCompanyExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CompanyModel) {
            Glide.with(itemView.context).load(item.logo).into(binding.imCompany)
            binding.TvCompanyName.text = item.companyName
            binding.tvCompanyCategory.text = item.companyCategory
            binding.tvRatingValue.text = item.companyRating
            with(itemView) {
                setOnClickListener {
                    Intent(context, CompanyExploreDetailActivity::class.java).apply {
                        context.startActivity(this)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchCompanyAdapter.MyViewHolder {
        val binding =
            RvItemCompanyExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCompanyAdapter.MyViewHolder, position: Int) {
        val companies = getItem(position)
        holder.bind(companies)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CompanyModel>() {
            override fun areContentsTheSame(oldItem: CompanyModel, newItem: CompanyModel): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CompanyModel, newItem: CompanyModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}