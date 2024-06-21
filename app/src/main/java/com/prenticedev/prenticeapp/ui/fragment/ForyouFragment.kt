package com.prenticedev.prenticeapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.ReviewModel
import com.prenticedev.prenticeapp.databinding.FragmentForyouBinding
import com.prenticedev.prenticeapp.ui.activity.SetUserPreferencesActivity
import com.prenticedev.prenticeapp.ui.adapter.ReviewCompanyAdapter
import com.prenticedev.prenticeapp.ui.viewmodel.ForYouViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory


class ForyouFragment : Fragment() {
    private lateinit var binding: FragmentForyouBinding

    private val forYouViewModel: ForYouViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForyouBinding.inflate(inflater, container, false)
        showLoading()
        showRecyclerList()

        binding.btnAddPreference.setOnClickListener {
            startActivity(Intent(requireActivity(), SetUserPreferencesActivity::class.java))
        }
        return binding.root
    }

    private fun showLoading() {
        forYouViewModel.isLoading.observe(viewLifecycleOwner) {
            checkLoadingStatus(it)
        }
    }

    private fun checkLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.addPrefWarning.visibility = View.GONE
            binding.rvForyou.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.addPrefWarning.visibility = View.VISIBLE
            binding.rvForyou.visibility = View.VISIBLE
        }
    }

    private fun showRecyclerList() {
        forYouViewModel.reviewFeedResponse.observe(viewLifecycleOwner) { feedData ->
            feedData?.let {
                if (it.status == 200) {
                    binding.addPrefWarning.visibility = View.GONE
                    binding.rvForyou.layoutManager = LinearLayoutManager(activity)
                    val feedAdapter = ReviewCompanyAdapter()
                    feedAdapter.submitList(it.data)
                    binding.rvForyou.adapter = feedAdapter
                } else {
                    showToast("Error: ${it.message.toString()}")
                }
            } ?: run {
                binding.rvForyou.visibility = View.GONE
                binding.addPrefWarning.visibility = View.VISIBLE
            }
//            if (feedData.data != null) {
//                binding.addPrefWarning.visibility = View.GONE
//                binding.rvForyou.layoutManager = LinearLayoutManager(activity)
//                val feedAdapter = ReviewCompanyAdapter()
//                feedAdapter.submitList(feedData.data)
//                binding.rvForyou.adapter = feedAdapter
//            } else {
//                binding.rvForyou.visibility = View.GONE
//                binding.addPrefWarning.visibility = View.VISIBLE
//            }
//            feedAdapter.submitList(it.data)
//            binding.rvForyou.adapter = feedAdapter
        }
//        forYouViewModel.reviewFeedDataResponse.observe(viewLifecycleOwner) {
//            binding.rvForyou.layoutManager = LinearLayoutManager(activity)
//            val reviewAdapter = ReviewCompanyAdapter()
//            reviewAdapter.submitList(it)
//            binding.rvForyou.adapter = reviewAdapter
//        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun getReviewData(): ArrayList<ReviewModel> {
        val imUser = resources.obtainTypedArray(R.array.users_photo)
        val name = resources.getStringArray(R.array.users)
        val location = resources.getStringArray(R.array.users_location)
        val role = resources.getStringArray(R.array.users_role)
        val status = resources.getStringArray(R.array.users_status)
        val reviewTitle = resources.getStringArray(R.array.users_review_title)
        val reviewContent = resources.getStringArray(R.array.users_review_content)

        val listReviews = ArrayList<ReviewModel>()
        for (i in name.indices) {
            val reviews = ReviewModel(
                imUser.getResourceId(i, -1),
                name[i],
                location[i],
                role[i],
                status[i],
                reviewTitle[i],
                reviewContent[i]
            )
            listReviews.add(reviews)
        }
        return listReviews
    }


}