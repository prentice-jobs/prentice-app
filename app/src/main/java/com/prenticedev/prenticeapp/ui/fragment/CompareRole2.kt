package com.prenticedev.prenticeapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.FragmentCompareRole2Binding

class CompareRole2 : Fragment() {
    private lateinit var binding: FragmentCompareRole2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompareRole2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve extras from the arguments
        val extraRole1 = arguments?.getString("extra_role1")
        val extraCompany1 = arguments?.getString("extra_company1")
        val extraDate1 = arguments?.getString("extra_date1")

        binding.buttonConfirm.setOnClickListener {
            val extraRole2 = binding.editTextSearchRoles.text.toString()
            val extraCompany2 = binding.editTextSearchCompany.text.toString()
            val extraDate2 = binding.editTextInputDate.text.toString()

            // Create a bundle to pass data to CompareResultsFragment
            val bundle = Bundle().apply {
                putString("extra_role1", extraRole1)
                putString("extra_company1", extraCompany1)
                putString("extra_date1", extraDate1)
                putString("extra_role2", extraRole2)
                putString("extra_company2", extraCompany2)
                putString("extra_date2", extraDate2)
            }

            // Create an instance of CompareResultsFragment and set the arguments
            val compareResultsFragment = CompareResultsFragment().apply {
                arguments = bundle
            }

            Log.d("CompareRole2", "Navigating to CompareResultsFragment with bundle: $bundle")

            // Perform fragment transaction
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_2, compareResultsFragment) // replace R.id.fragment_container with your container id
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {

    }
}