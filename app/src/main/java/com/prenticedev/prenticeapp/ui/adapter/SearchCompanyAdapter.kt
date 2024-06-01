package com.prenticedev.prenticeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.CompanyModel

class SearchCompanyAdapter(private val listCompanies: ArrayList<CompanyModel>) :
    RecyclerView.Adapter<SearchCompanyAdapter.MyViewHolder>() {
    private lateinit var onItemCallback: OnItemCallback

    interface OnItemCallback {
        fun onItemClicked(data: CompanyModel)
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val companyName: TextView = itemview.findViewById(R.id.TvCompanyName)
        val companyCategory: TextView = itemview.findViewById(R.id.tvCompanyCategory)
        val companyRating: TextView = itemview.findViewById(R.id.tvRatingValue)
        val companyLogo: ImageView = itemview.findViewById(R.id.imCompany)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchCompanyAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_company_explore, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCompanyAdapter.MyViewHolder, position: Int) {
        val (companyName, companyCategory, companyRating, imCompany) = listCompanies[position]
        holder.companyName.text =companyName
        holder.companyCategory.text = companyCategory
        holder.companyRating.text = companyRating
        holder.companyLogo.setImageResource(imCompany)
    }

    override fun getItemCount(): Int = listCompanies.size
}