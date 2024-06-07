package com.prenticedev.prenticeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.dummydata.Company
import com.prenticedev.prenticeapp.databinding.ItemCompanyBinding

class ListCompanyAdapter(private val listCompany: ArrayList<Company>) : RecyclerView.Adapter<ListCompanyAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, location, postDate, reviewTitle, reviewContent) = listCompany[position]
        holder.bind(name, location, postDate, reviewTitle, reviewContent)
    }

    override fun getItemCount(): Int = listCompany.size

    class ListViewHolder(private val binding: ItemCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, location: String, postDate: String, reviewTitle: String, reviewContent: String) {
            binding.companyName.text = name
            binding.companyLocation.text = location
            binding.companyPostDate.text = postDate
            binding.reviewTitle.text = reviewTitle
            binding.reviewContent.text = reviewContent
        }
    }
}