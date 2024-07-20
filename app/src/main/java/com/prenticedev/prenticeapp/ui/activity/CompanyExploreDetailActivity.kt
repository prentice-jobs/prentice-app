package com.prenticedev.prenticeapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.ReviewModel
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivityCompanyExploreDetailBinding
import com.prenticedev.prenticeapp.ui.adapter.DetailCompanyAdapter
import com.prenticedev.prenticeapp.ui.viewmodel.CompanyExploreDetailViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class CompanyExploreDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyExploreDetailBinding
    private lateinit var extra_id: String
    private val companyExploreDetailViewModel: CompanyExploreDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiConfig.initialize(this)
        binding = ActivityCompanyExploreDetailBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        extra_id = intent.getStringExtra(EXTRA_ID).toString()

        companyExploreDetailViewModel.getDetailCompany(extra_id)
        setCompanyData()
        showRVData()
        showSentimentScore()

        companyExploreDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAddReview.setOnClickListener {
            addReview()
        }
    }

    private fun addReview() {
        companyExploreDetailViewModel.detailCompanyData.observe(this) {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("company_id", extra_id)
            intent.putExtra("company_name", it.data?.displayName)
            startActivity(intent)
        }

    }

    private fun showLoading(isLoading: Boolean) =
        if (isLoading) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE


    private fun setCompanyData() {
        companyExploreDetailViewModel.detailCompanyData.observe(this) {
            Glide.with(this).load(it.data?.logoUrl).into(binding.imCompany)
            binding.tvCompanyTitle.text = it.data?.displayName
            binding.companyName.text = it.data?.displayName
            binding.tvRatingCount.text = it.data?.starRating.toString()
            binding.tvCompanyDesc.text = it.data?.description

        }
    }

    private fun showSentimentScore() {
        binding.barSentiment.setPercentage(50, 50, 50)
    }


    private fun showRVData() {
//        list.addAll(getReviewData())
        binding.rvReviews.layoutManager = LinearLayoutManager(this)
        val reviewAdapter = DetailCompanyAdapter()
        companyExploreDetailViewModel.detailCompanyData.observe(this) {
            reviewAdapter.submitList(it.data?.companyReviews)
        }
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

    companion object {
        const val EXTRA_ID = "extra_id"
        private val TAG = CompanyExploreDetailActivity::class.java.simpleName
    }
}