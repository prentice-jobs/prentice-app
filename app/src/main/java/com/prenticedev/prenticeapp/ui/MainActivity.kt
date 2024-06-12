package com.prenticedev.prenticeapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pressedBack = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiConfig.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            Glide.with(this).load(firebaseUser.photoUrl).into(binding.imUserProfile)
        } else {
            Glide.with(this).load(R.drawable.baseline_profile_circle_24).into(binding.imUserProfile)
        }

        binding.imUserProfile.setOnClickListener {
            startActivity(Intent(this,UserProfileActivity::class.java))
        }

        val navView: BottomNavigationView = binding.navView
        navView.selectedItemId = R.id.navigation_foryou
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_foryou -> {
                    replaceFragment(ForyouFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_explore -> {
                    replaceFragment(ExploreFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_compare -> {
                    replaceFragment(CompareFragment())
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
        val doubleBackToExitPressedOnce = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (pressedBack) {
                    finishAffinity()
                } else {
                    pressedBack = true
                    Toast.makeText(
                        this@MainActivity,
                        "Please click BACK again to exit",
                        Toast.LENGTH_SHORT
                    ).show()
                    view.postDelayed({ pressedBack = false }, 2000)
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(
            this,
            doubleBackToExitPressedOnce
        )

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentView, fragment)
            .commit()
    }

}
