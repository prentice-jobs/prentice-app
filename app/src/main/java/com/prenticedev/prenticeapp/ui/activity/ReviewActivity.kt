package com.prenticedev.prenticeapp.ui.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.helper.reduceImage
import com.prenticedev.prenticeapp.data.helper.uriToFile
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivityReviewBinding
import com.prenticedev.prenticeapp.ui.viewmodel.ReviewViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private lateinit var extra_id: String
    private var isRemote: Boolean = false
    private var currentImageUri: Uri? = null
    private val tags = mutableListOf<String>()
    private val reviewViewModel: ReviewViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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
        addRoleSpinnerValue()
        addLocationSpinnerValue()
        isRemote = binding.isRemote.isActivated

        reviewViewModel.isLoading.observe(this) {
            showLoading(it)
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
            uploadReview()
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

//        TODO: MAKE ROUTE TO ANOTHER PAGE WHEN SUCCESSFULLY INSERTED A DATA REVIEW
        reviewViewModel.makeReviewResponse.observe(this) {
            if (it.status == 201) {
                showToast("Review Successfully Added!: ${it.message.toString()}")
                startActivity(Intent(this, CompanyExploreDetailActivity::class.java).apply {
                    putExtra(CompanyExploreDetailActivity.EXTRA_ID, extra_id)
                })
                finish()
            } else {
                showToast("Add Review Failed: ${it.message.toString()}")
            }
        }

    }

    private fun addLocationSpinnerValue() {
        reviewViewModel.locationSpinnerValue.observe(this) { locResponse ->
            locResponse.data?.let { locations ->
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    locations.filterNotNull()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerLocation.adapter = adapter
            }
        }
    }

    private fun addRoleSpinnerValue() {
        reviewViewModel.roleSpinnerValue.observe(this) { roleResponse ->
            roleResponse.data?.let { roles ->
                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, roles.filterNotNull())
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinRole.adapter = adapter
            }
        }
    }

    private fun uploadReview() {
        val companyId = extra_id
        val location = binding.spinnerLocation.selectedItem.toString()
        val starRating = binding.ratingBar.rating
        val title = binding.edtTitleReview.text.toString()
        val description = binding.edtDescReview.text.toString()
        val startDate = binding.dateStart.text.toString()
        val endDate = binding.dateEnd.text.toString()
        val annualSalary = binding.edtAnnualSalary.text.toString()

        currentImageUri?.let { uri ->
            showLoading(true)
            val imFile = uriToFile(uri, this).reduceImage()
            reviewViewModel.uploadOfferLetterImage(imFile)

            reviewViewModel.uploadedImageUrl.observe(this) {
                reviewViewModel.postReview(
                    companyId,
                    location,
                    isRemote,
                    tags,
                    starRating,
                    title,
                    description,
                    binding.spinRole.selectedItem.toString(),
                    startDate,
                    endDate,
                    it,
                    annualSalary,
                    binding.spinnerCurrency.selectedItem.toString()
                )

            }
            reviewViewModel.isLoading.observe(this){
                showLoading(it)
            }
        } ?: showToast("Please Add Offer Letter Image")

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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