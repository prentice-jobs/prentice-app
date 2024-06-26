package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.CompanyModel
import com.prenticedev.prenticeapp.databinding.FragmentExploreBinding
import com.prenticedev.prenticeapp.ui.adapter.SearchCompanyAdapter


class ExploreFragment : Fragment() {
    private val list = ArrayList<CompanyModel>()
    private lateinit var originalList: List<CompanyModel>
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        list.addAll(getCompanyList())
        originalList = ArrayList(list)
        showRecyclerList()

        setupSearchView()
        setupChipFilter()

//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterResult(newText.orEmpty())
//                return true
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//        })


        return binding.root
    }

    private fun setupChipFilter() {
        binding.quickF.setOnCheckedStateChangeListener { group, checkedId ->
            val selectedCategory = checkedId.map { id ->
                val selectedChip = group.findViewById<Chip>(id)
                selectedChip?.text?.toString() ?: ""
            }
            filterByCategory(selectedCategory)

        }
    }

    private fun filterByCategory(category: List<String>) {
        list.clear()
        if (category.isEmpty()) {
            list.addAll(originalList)
        } else {
            val filteredResult = originalList.filter { company ->
                category.any { category ->
                    company.companyCategory.equals(category, ignoreCase = true)
                }
            }
            list.addAll(filteredResult)
        }
        binding.rvCompanies.adapter?.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                filterResult(newText.orEmpty())
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun filterResult(query: String) {
        list.clear()
        if (query.isEmpty()) {
            list.addAll(originalList)
        } else {
            val filteredResult = originalList.filter {
                it.companyName.contains(
                    query,
                    ignoreCase = true
                ) || it.companyRating.contains(query, ignoreCase = true)
            }
            list.addAll(filteredResult)
        }
        binding.rvCompanies.adapter?.notifyDataSetChanged()
    }

    private fun showRecyclerList() {
        binding.rvCompanies.layoutManager = LinearLayoutManager(activity)
        val listCompanyAdapter = SearchCompanyAdapter()
        listCompanyAdapter.submitList(list)
        binding.rvCompanies.adapter = listCompanyAdapter
    }

    private fun getCompanyList(): ArrayList<CompanyModel> {
        val companyName = resources.getStringArray(R.array.company_name)
        val companyRating = resources.getStringArray(R.array.company_rating)
        val companyLogo = resources.obtainTypedArray(R.array.company_logo)
        val companyCategory = resources.getStringArray(R.array.company_category)

        val listCompany = ArrayList<CompanyModel>()
        for (i in companyName.indices) {
            val company = CompanyModel(
                companyName[i],
                companyCategory[i],
                companyRating[i],
                companyLogo.getResourceId(i, -1),
            )
            listCompany.add(company)
        }
        return listCompany
    }

}