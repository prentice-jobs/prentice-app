package com.prenticedev.prenticeapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.ActivityUserProfileBinding
import com.prenticedev.prenticeapp.ui.viewmodel.UserProfileViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val userProfileViewModel: UserProfileViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            Glide.with(this).load(firebaseUser.photoUrl).into(binding.imProfile)
            binding.tvUserProfile.text = firebaseUser.displayName
            binding.tvUID.text = firebaseUser.uid
        } else {
            Glide.with(this).load(R.drawable.baseline_profile_circle_24).into(binding.imProfile)
            binding.tvUserProfile.text = "Anonymous"
            binding.tvUID.text = ""

        }


        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            signOut()
        }

    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                lifecycleScope.launch {
                    userProfileViewModel.deleteSession()
                }
                finish()
                Toast.makeText(this, "Sign out success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}