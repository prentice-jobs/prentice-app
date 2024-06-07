package com.prenticedev.prenticeapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.local.model.CommentModel
import com.prenticedev.prenticeapp.databinding.ActivityDetailReviewBinding
import com.prenticedev.prenticeapp.ui.adapter.CommentReviewAdapter

class DetailReviewActivity : AppCompatActivity() {
    private val list = ArrayList<CommentModel>()
    private lateinit var binding: ActivityDetailReviewBinding
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
        showRVComment()
        binding.btnAddComment.setOnClickListener {
            val intent = Intent(this, CommentReviewActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
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
}