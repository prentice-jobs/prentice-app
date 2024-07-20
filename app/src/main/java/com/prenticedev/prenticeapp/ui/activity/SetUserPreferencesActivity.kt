package com.prenticedev.prenticeapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivitySetUserPreferencesBinding
import com.prenticedev.prenticeapp.ui.viewmodel.SetUserPreferencesViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class SetUserPreferencesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetUserPreferencesBinding
    private val setUserPreferencesViewModel: SetUserPreferencesViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserPreferencesBinding.inflate(layoutInflater)
        ApiConfig.initialize(this)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        isLoading()
        addSpinnerValue()
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSavePref.setOnClickListener {
            submitPreferenceData()
        }
    }

    private fun submitPreferenceData() {
        val spinRoleValue = binding.spinRole.selectedItem.toString()
        val spinIndustryValue = binding.spinIndustry.selectedItem.toString()
        val spinLocationValue = binding.spinLocation.selectedItem.toString()

        setUserPreferencesViewModel.submitPreference(
            role = spinRoleValue,
            industry = spinIndustryValue,
            location = spinLocationValue
        )
        updateUI()
    }

    private fun updateUI() {
        setUserPreferencesViewModel.savePreferenceResponse.observe(this) { savePrefResoponse ->
            savePrefResoponse?.let {
                if (it.error != null) {
                    showToast("Your preference is successfully added!")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    showToast("Set User Preference is Failed")
                }
            } ?: run {
                showToast("Your preference is successfully added!")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun addSpinnerValue() {
        setUserPreferencesViewModel.rolesResponse.observe(this) { rolesResponse ->
            rolesResponse.data?.let { roles ->
                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, roles.filterNotNull())
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinRole.adapter = adapter
            }
        }
        setUserPreferencesViewModel.locationsResponse.observe(this) { locationResponse ->
            locationResponse.data?.let { location ->
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    location.filterNotNull()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinLocation.adapter = adapter
            }
        }
        setUserPreferencesViewModel.industriesResponse.observe(this) { industriesResponse ->
            industriesResponse.data?.let { industries ->
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    industries.filterNotNull()
                )
                binding.spinIndustry.adapter = adapter
            }
        }
    }

    private fun isLoading() {
        setUserPreferencesViewModel.isLoading.observe(this) { loadingStatus ->
            if (loadingStatus) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }


}