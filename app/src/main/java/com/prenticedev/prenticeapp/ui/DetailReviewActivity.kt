package com.prenticedev.prenticeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.CommentModel
import com.prenticedev.prenticeapp.databinding.ActivityDetailReviewBinding
import com.prenticedev.prenticeapp.ui.adapter.CommentReviewAdapter
import com.prenticedev.prenticeapp.ui.viewmodel.DetailReviewViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class DetailReviewActivity : AppCompatActivity() {
    private val list = ArrayList<CommentModel>()
    private lateinit var binding: ActivityDetailReviewBinding
    private lateinit var extraId: String
    private val detailReviewViewModel: DetailReviewViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReviewBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        extraId = intent.getStringExtra(EXTRA_ID).toString()
        binding.btnAddComment.setOnClickListener {
            val intent = Intent(this, CommentReviewActivity::class.java)
            startActivity(intent)
        }
        showLoading()
        showRVComment()
        showReviewData()

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun showLoading() {
        detailReviewViewModel.isLoading.observe(this) {
            isLoading(it)
        }
    }

    private fun isLoading(loadingStatus: Boolean) {
        if (loadingStatus) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showReviewData() {
        detailReviewViewModel.getDetailReview(extraId)
        detailReviewViewModel.detailReviewResponse.observe(this) {
            binding.tvDetailRevTitle.text = it.data?.review?.title.toString()
            binding.tvTime.text = it.data?.review?.createdAt.toString()
        }
    }

    private fun showRVComment() {
        binding.rvUserComment.layoutManager = LinearLayoutManager(this)
        list.addAll(getCommentData())
        val adapter = CommentReviewAdapter()
        adapter.submitList(list)
        binding.rvUserComment.adapter = adapter
    }

    private fun getCommentData(): ArrayList<CommentModel> {
        val userName = resources.getStringArray(R.array.users)
        val userAvatar = resources.obtainTypedArray(R.array.users_photo)
        val userComment = resources.getStringArray(R.array.users_review_content)

        val listComment = ArrayList<CommentModel>()
        for (i in userName.indices) {
            val comments = CommentModel(
                userAvatar.getResourceId(i, -1),
                userName[i],
                userComment[i]
            )
            listComment.add(comments)
        }
        return listComment
    }

    companion object {
        private val TAG = DetailReviewActivity::class.java.simpleName
        const val EXTRA_ID = "extra_id"
    }
}