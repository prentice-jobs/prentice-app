package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.FragmentCompareBinding
import com.prenticedev.prenticeapp.databinding.FragmentCompareResultsBinding
import com.prenticedev.prenticeapp.databinding.FragmentCompareRole2Binding

class CompareResultsFragment : Fragment() {

    private lateinit var binding: FragmentCompareResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and get an instance of the binding
        binding = FragmentCompareResultsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve extras from the arguments
        val extraRole1 = arguments?.getString("extra_role1")
        val extraCompany1 = arguments?.getString("extra_company1")
        val extraDate1 = arguments?.getString("extra_date1")

        val extraRole2 = arguments?.getString("extra_role2")
        val extraCompany2 = arguments?.getString("extra_company2")
        val extraDate2 = arguments?.getString("extra_date2")
    }
}