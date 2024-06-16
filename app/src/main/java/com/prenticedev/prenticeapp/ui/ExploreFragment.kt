package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.databinding.FragmentExploreBinding
import com.prenticedev.prenticeapp.ui.adapter.SearchCompanyAdapter
import com.prenticedev.prenticeapp.ui.viewmodel.ExploreViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory


class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private val exploreViewModel: ExploreViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

//    TODO: PERBAIKI UI ADD REVIEW DAN JUGA LANJUTKAN FITUR QUICK FILTER PADA TAG
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        showRecyclerList()
        observeViewModel()


        setupSearchView()
//        setupChipFilter()
//        showDataFromAPI()

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

    private fun observeViewModel() {
        exploreViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        exploreViewModel.companyData.observe(viewLifecycleOwner) { companies ->
            (binding.rvCompanies.adapter as SearchCompanyAdapter).submitList(companies)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        exploreViewModel.isLoading.observe(viewLifecycleOwner) {
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
//    private fun setupChipFilter() {
//        binding.quickF.setOnCheckedStateChangeListener { group, checkedId ->
//            val selectedCategory = checkedId.map { id ->
//                val selectedChip = group.findViewById<Chip>(id)
//                selectedChip?.text?.toString() ?: ""
//            }
//            filterByCategory(selectedCategory)
//
//        }
//    }

//    private fun filterByCategory(category: List<String>) {
//        list.clear()
//        if (category.isEmpty()) {
//            list.addAll(originalList)
//        } else {
//            val filteredResult = originalList.filter { company ->
//                category.any { category ->
//                    company.companyCategory.equals(category, ignoreCase = true)
//                }
//            }
//            list.addAll(filteredResult)
//        }
//        binding.rvCompanies.adapter?.notifyDataSetChanged()
//    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.orEmpty().isEmpty()){
                    exploreViewModel.getCompanies()
                }else{
                    exploreViewModel.searchCompanyByName(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }
    private fun showRecyclerList() {
        binding.rvCompanies.layoutManager = LinearLayoutManager(activity)
        val listCompanyAdapter = SearchCompanyAdapter()
        exploreViewModel.companyData.observe(viewLifecycleOwner) {
            listCompanyAdapter.submitList(it)
        }
        binding.rvCompanies.adapter = listCompanyAdapter
    }

}