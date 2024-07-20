package com.prenticedev.prenticeapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.FragmentCompareBinding
import com.prenticedev.prenticeapp.ui.viewmodel.CompareFragmentViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class CompareFragment : Fragment() {
    private lateinit var binding: FragmentCompareBinding

    private val compareFragmentViewModel: CompareFragmentViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerData()

        binding.buttonConfirm.setOnClickListener {
//            val extraRole1 = binding.editTextSearchRoles.text.toString()
//            val extraCompany1 = binding.editTextSearchCompany.text.toString()
//            val extraDate1 = binding.editTextInputDate.text.toString()

            // Create a bundle to pass data to CompareRole2 fragment
            val bundle = Bundle().apply {
//                putString("extra_role1", extraRole1)
//                putString("extra_company1", extraCompany1)
//                putString("extra_date1", extraDate1)
            }

            // Create an instance of CompareRole2 and set the arguments
            val compareRole2Fragment = CompareRole2().apply {
                arguments = bundle
            }

            // Perform fragment transaction
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_1,
                    compareRole2Fragment
                ) // replace R.id.fragment_container with your container id
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setSpinnerData() {
        compareFragmentViewModel.roleList.observe(viewLifecycleOwner) { roleData ->
            roleData.data?.let { roles ->
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        roles.filterNotNull()
                    )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSearchRole.adapter = adapter
            }
        }

        compareFragmentViewModel.companyList.observe(viewLifecycleOwner) { companyData ->
            companyData?.data?.let { companies ->
                val companyName = companies.map { it?.displayName ?: "Unknown" }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    companyName
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCompany.adapter = adapter
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}