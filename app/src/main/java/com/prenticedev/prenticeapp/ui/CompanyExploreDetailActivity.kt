package com.prenticedev.prenticeapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.ReviewModel
import com.prenticedev.prenticeapp.databinding.ActivityCompanyExploreDetailBinding
import com.prenticedev.prenticeapp.ui.adapter.ReviewCompanyAdapter

class CompanyExploreDetailActivity : AppCompatActivity() {
    private val list = ArrayList<ReviewModel>()
    private lateinit var binding: ActivityCompanyExploreDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyExploreDetailBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnAddReview.setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java))
        }
        showRVData()
        showSentimentScore()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showSentimentScore() {
        binding.barSentiment.setPercentage(50,50,50)
    }


    private fun showRVData() {
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        list.addAll(getReviewData())
        val reviewAdapter = ReviewCompanyAdapter()
        reviewAdapter.submitList(list)
        binding.rvReviews.adapter = reviewAdapter
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