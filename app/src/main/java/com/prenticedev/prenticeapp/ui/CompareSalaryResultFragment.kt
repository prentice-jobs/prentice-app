package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.response.ApiConfig
import com.prenticedev.prenticeapp.data.response.CompareSalaryRequest
import com.prenticedev.prenticeapp.data.response.CompareSalaryResponse
import com.prenticedev.prenticeapp.data.response.DataItem
import com.prenticedev.prenticeapp.databinding.FragmentCompareResultsBinding
import com.prenticedev.prenticeapp.databinding.FragmentCompareSalaryResultBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException


class CompareSalaryResultFragment : Fragment() {
    private lateinit var binding: FragmentCompareSalaryResultBinding
    private var listData: List<DataItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and get an instance of the binding
        binding = FragmentCompareSalaryResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve extras from the arguments
        val extraRole1 = arguments?.getString("extra_role1")
        val extraCompany1 = arguments?.getString("extra_company1")
        val extraLocation1 = arguments?.getString("extra_location1")

        val extraRole2 = arguments?.getString("extra_role2")
        val extraCompany2 = arguments?.getString("extra_company2")
        val extraLocation2 = arguments?.getString("extra_location2")

        // Handle null values by using empty strings or some default values
        val extraRoles = listOfNotNull(extraRole1, extraRole2)
        val extraCompanies = listOfNotNull(extraCompany1, extraCompany2)
        val extraLocations = listOfNotNull(extraLocation1, extraLocation2)

        // Only call getCompare if we have exactly two items in each list
        if (extraRoles.size == 2 && extraCompanies.size == 2 && extraLocations.size == 2) {
            getCompare(extraRoles, extraCompanies, extraLocations)
        } else {
            // Handle the case where the required arguments are not provided
            // You might want to show an error message or perform some other action
        }
    }

    private fun getCompare(
        roles: List<String>,
        companies: List<String>,
        locations: List<String>
    ) {
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val request = CompareSalaryRequest(
                    roles_compare_salary = roles,
                    companies_compare_salary = companies,
                    locations_compare_salary = locations
                )
                val successResponse = apiService.compareSalary(request)
                val dataItem = successResponse.getData()

                if (dataItem.size >= 2) {
                    binding.role1.text = dataItem[0].role
                    binding.role2.text = dataItem[1].role
                    binding.date1.text = dataItem[0].datePosted
                    binding.date2.text = dataItem[1].datePosted

                    Glide.with(this@CompareSalaryResultFragment)
                        .load(dataItem[0].logoUrl) // URL Gambar
                        .into(binding.logo1) // imageView mana yang akan diterapkan

                    Glide.with(this@CompareSalaryResultFragment)
                        .load(dataItem[1].logoUrl) // URL Gambar
                        .into(binding.logo2) // imageView mana yang akan diterapkan

                    binding.company1.text = dataItem[0].company
                    binding.company2.text = dataItem[1].company
                    binding.salary1.text = dataItem[0].annualSalary.toString()
                    binding.salary2.text = dataItem[1].annualSalary.toString()
                    binding.rating1.text = dataItem[0].companyRating.toString()
                    binding.rating2.text = dataItem[1].companyRating.toString()
                } else {
                    // Handle the case where the response does not contain the expected number of data items
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, CompareSalaryResponse::class.java)
                // Handle errorResponse appropriately
            }
        }
    }
}