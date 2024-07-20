package com.prenticedev.prenticeapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prenticedev.prenticeapp.databinding.FragmentCompareResultBinding


class CompareResultsFragment : Fragment() {
    private lateinit var binding: FragmentCompareResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompareResultBinding.inflate(layoutInflater)
        return binding.root
    }

//    TODO: NEED ADDED THE DISPLAY RESULT VIEW LAYOUT

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