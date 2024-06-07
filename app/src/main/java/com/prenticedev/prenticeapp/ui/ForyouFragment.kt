package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.ReviewModel
import com.prenticedev.prenticeapp.databinding.FragmentForyouBinding
import com.prenticedev.prenticeapp.ui.adapter.ReviewCompanyAdapter


class ForyouFragment : Fragment() {
    private lateinit var binding: FragmentForyouBinding
    private val list = ArrayList<ReviewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForyouBinding.inflate(inflater, container, false)
        showRecyclerList()
        return binding.root
    }
    private fun showRecyclerList() {
        binding.rvForyou.layoutManager = LinearLayoutManager(activity)
        list.addAll(getReviewData())
        val reviewAdapter = ReviewCompanyAdapter()
        reviewAdapter.submitList(list)
        binding.rvForyou.adapter =reviewAdapter
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