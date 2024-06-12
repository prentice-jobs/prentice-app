package com.prenticedev.prenticeapp.ui

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private lateinit var extra_id: String
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReviewBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        extra_id = intent.getStringExtra("company_id").toString()
        val companyName = intent.getStringExtra("company_name")
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvCompanyTitle.text = companyName.toString()
        binding.TvCompanyName.text = "Review ${companyName.toString()}"

        binding.btnAddOffer.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            binding.tvFileName.visibility = View.VISIBLE
            val fileName = getFileNameFromUri(uri)
            binding.tvFileName.text = fileName
            binding.tvFileName.setOnClickListener {
                showImagePreview(uri)
            }
            binding.btnAddOffer.text = "Edit File"
        }
    }

    private fun showImagePreview(uri: Uri) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_image_preview)
        val imView: ImageView = dialog.findViewById(R.id.imOfferPreview)
        Glide.with(this).load(uri).into(imView)
        dialog.show()
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        var fileName: String? = null
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                cursor.moveToFirst()
                fileName = cursor.getString(nameIndex)
            }

        }
        return fileName
    }


    companion object {
        private val TAG = ReviewActivity::class.java
        const val EXTRA_ID = "extra_id"
    }
}