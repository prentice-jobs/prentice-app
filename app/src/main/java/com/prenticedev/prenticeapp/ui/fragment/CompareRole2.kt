package com.prenticedev.prenticeapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.FragmentCompareRole2Binding
import com.prenticedev.prenticeapp.ui.viewmodel.CompareRole2ViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class CompareRole2 : Fragment() {
    private lateinit var binding: FragmentCompareRole2Binding

    private val compareRole2ViewModel: CompareRole2ViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            setSpinnerData()
////            val extraRole2 = binding.editTextSearchRoles.text.toString()
//            val extraCompany2 = binding.editTextSearchCompany.text.toString()
//            val extraDate2 = binding.editTextInputDate.text.toString()

            // Create a bundle to pass data to CompareResultsFragment
            val bundle = Bundle().apply {
//                putString("extra_role1", extraRole1)
//                putString("extra_company1", extraCompany1)
//                putString("extra_date1", extraDate1)
//                putString("extra_role2", extraRole2)
//                putString("extra_company2", extraCompany2)
//                putString("extra_date2", extraDate2)
            }

            // Create an instance of CompareResultsFragment and set the arguments
            val compareResultsFragment = CompareResultsFragment().apply {
                arguments = bundle
            }

            Log.d("CompareRole2", "Navigating to CompareResultsFragment with bundle: $bundle")

            // Perform fragment transaction
            parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container_2, compareResultsFragment
                ) // replace R.id.fragment_container with your container id
                .addToBackStack(null).commit()
        }
    }

    private fun setSpinnerData() {
        compareRole2ViewModel.roleList.observe(viewLifecycleOwner) { roleData ->
            roleData.data?.let { roles ->
                val adapter = ArrayAdapter(
                    requireContext(), android.R.layout.simple_spinner_item, roles.filterNotNull()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSearchRole.adapter = adapter
            }
        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }
}

