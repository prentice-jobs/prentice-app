package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.dummydata.Company
import com.prenticedev.prenticeapp.databinding.FragmentForyouBinding


class ForyouFragment : Fragment() {
    private lateinit var binding: FragmentForyouBinding
    private val list = ArrayList<Company>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentForyouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvForyou.setHasFixedSize(true)
        binding.rvForyou.layoutManager = LinearLayoutManager(context)

        list.addAll(getListCompany())
        showRecyclerList()
    }

    private fun getListCompany(): ArrayList<Company> {
        val companyName = resources.getStringArray(R.array.company_name)
        val companyLocation = resources.getStringArray(R.array.company_location)
        val companyPostDate = resources.getStringArray(R.array.company_postdate)
        val reviewTitle = resources.getStringArray(R.array.review_title)
        val reviewContent = resources.getStringArray(R.array.review_content)

        val listCompany = ArrayList<Company>()
        for (i in companyName.indices) {
            val company = Company(companyName[i], companyLocation[i], companyPostDate[i], reviewTitle[i], reviewContent[i])
            listCompany.add(company)
        }
        return listCompany
    }

    private fun showRecyclerList() {
        val listCompanyAdapter = ListCompanyAdapter(list)
        binding.rvForyou.adapter = listCompanyAdapter
    }
}