package com.prenticedev.prenticeapp.ui

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private lateinit var extra_id: String
    private var currentImageUri: Uri? = null
    private val tags = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        val view = binding.root
        ApiConfig.initialize(this)
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

        binding.btnSubmitReview.setOnClickListener {
            showRatingValue()
        }

        binding.btnAddTag.setOnClickListener {
            addTag()
        }

        binding.chipTag.setOnCheckedChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds)
            if (chip != null) {
                removeTag(chip.text.toString())
                group.removeView(chip)
            }

        }
    }

    private fun removeTag(tag: String) {
        tags.remove(tag)
    }

    private fun addTag() {
        val tag = binding.edtTag.text.toString()
        if (tag.isNotEmpty()) {
            tags.add(tag)
            val chip = Chip(this).apply {
                text = tag
                isCloseIconVisible = true
                setOnClickListener {
                    binding.chipTag.removeView(this)
                    tags.remove(tag)
                }
            }
            binding.chipTag.addView(chip)
            binding.edtTag.text.clear()
        }
    }

    private fun showRatingValue() {
        val starValue = binding.ratingBar.rating.toString()
        showToast(starValue)
    }

    private fun showToast(starValue: String) {
        Toast.makeText(this, "The star value is: $starValue", Toast.LENGTH_SHORT).show()
    }

    private fun checkSubmit() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_show_submit_review_data)
        val title: TextView = dialog.findViewById(R.id.title)
        val content: TextView = dialog.findViewById(R.id.content)
        val role: TextView = dialog.findViewById(R.id.role)
        val startDate: TextView = dialog.findViewById(R.id.startdate)
        val endDate: TextView = dialog.findViewById(R.id.endate)
        val currency: TextView = dialog.findViewById(R.id.currency)
        val amount: TextView = dialog.findViewById(R.id.amount)
        val isRemote: CheckBox = dialog.findViewById(R.id.isRemote)

        title.text = binding.edtTitleReview.text.toString()
        content.text = binding.edtDescReview.text.toString()
        role.text = binding.spinRole.selectedItem.toString()
        startDate.text = binding.dateStart.text.toString()
        endDate.text = binding.dateEnd.text.toString()
        currency.text = binding.spinnerCurrency.selectedItem.toString()
        amount.text = binding.edtAnnualSalary.text.toString()
        if (isRemote.isChecked) {
            isRemote.text = "Yes"
        } else {
            isRemote.text = "No"
        }
        dialog.show()
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