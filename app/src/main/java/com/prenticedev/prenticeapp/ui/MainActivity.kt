package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pressedBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        val navView: BottomNavigationView = binding.navView
        navView.selectedItemId =R.id.navigation_foryou
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_foryou -> {
                    replaceFragment(ForyouFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_explore ->{
                    replaceFragment(ExploreFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_compare ->{
                    replaceFragment(CompareFragment())
                    return@setOnItemSelectedListener true
                }
                else ->false
            }
        }
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_foryou, R.id.navigation_explore, R.id.navigation_compare
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

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
                    view?.postDelayed({ pressedBack = false }, 2000)
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(
            this,
            doubleBackToExitPressedOnce
        )

    }

        private fun replaceFragment(fragment: Fragment){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentView, fragment)
                .commit()
        }

//         firebaseAuth = FirebaseAuth.getInstance()
//         val firebaseUser = firebaseAuth.currentUser

//         if (firebaseUser != null) {
//             Glide.with(this@MainActivity).load(firebaseUser.photoUrl).into(binding.imUser)
//             binding.nama.text = firebaseUser.displayName
//             binding.userUID.text = firebaseUser.uid
//         }else{
//             Glide.with(this@MainActivity).load(R.drawable.baseline_profile_circle_24).into(binding.imUser)
//             binding.nama.text = "Anonymous"
//             binding.userUID.text ="Empty"
//             binding.btnSignOut.visibility = View.GONE
//         }

//         mGoogleSignInClient =
//             GoogleSignIn.getClient(this@MainActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
//         binding.btnSignOut.setOnClickListener {
//             mGoogleSignInClient.signOut().addOnCompleteListener { task ->
//                 if (task.isSuccessful) {
//                     firebaseAuth.signOut()
//                     Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
//                     finish()
//                 }
//             }
//         }


//         val doubleBackToExitPressedOnce = object : OnBackPressedCallback(true) {
//             override fun handleOnBackPressed() {
//                 if (pressedBack) {
//                     finishAffinity()
//                 }
//                 pressedBack = true
//                 Toast.makeText(
//                     this@MainActivity,
//                     "Please click BACK again to exit",
//                     Toast.LENGTH_SHORT
//                 ).show()
//                 view.postDelayed({ pressedBack = false }, 2000)
//             }
//         }

}
