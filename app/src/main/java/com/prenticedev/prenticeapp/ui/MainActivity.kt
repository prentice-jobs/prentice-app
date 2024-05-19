package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var pressedBack = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            Glide.with(this@MainActivity).load(firebaseUser.photoUrl).into(binding.imUser)
            binding.nama.text = firebaseUser.displayName
            binding.userUID.text = firebaseUser.uid
        }else{
            Glide.with(this@MainActivity).load(R.drawable.baseline_profile_circle_24).into(binding.imUser)
            binding.nama.text = "Anonymous"
            binding.userUID.text ="Empty"
            binding.btnSignOut.visibility = View.GONE
        }

        mGoogleSignInClient =
            GoogleSignIn.getClient(this@MainActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        binding.btnSignOut.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.signOut()
                    Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        val doubleBackToExitPressedOnce = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (pressedBack) {
                    finishAffinity()
                }
                pressedBack = true
                Toast.makeText(
                    this@MainActivity,
                    "Please click BACK again to exit",
                    Toast.LENGTH_SHORT
                ).show()
                view.postDelayed({ pressedBack = false }, 2000)
            }
        }
        onBackPressedDispatcher.addCallback(this, doubleBackToExitPressedOnce)
    }

}