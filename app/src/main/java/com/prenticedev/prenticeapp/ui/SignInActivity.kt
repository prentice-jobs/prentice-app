package com.prenticedev.prenticeapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.prenticedev.prenticeapp.R
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.databinding.ActivitySignInBinding
import com.prenticedev.prenticeapp.ui.viewmodel.SignInViewModel
import com.prenticedev.prenticeapp.ui.viewmodel.ViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private val signInID = 100
    private val signInViewModel: SignInViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiConfig.initialize(this)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        if (currentUser != null) {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInViewModel.authState.observe(this) {
            updateUI(user = it)
        }
        signInViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        signInViewModel.registerResponse.observe(this) { regData ->
            signInViewModel.isUserExist.observe(this) { isExist ->
                if (isExist) {
                    showToast("Welcome Back ${regData.data?.email}")
                } else {
                    showToast("New User Has Been Registered! Welcome ${regData.data?.email}")
                    Log.d(TAG, regData.message.toString())
                }

            }
        }

        binding.btnSignGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, signInID)
        }
        binding.btnSignGuest.setOnClickListener {
//            val intent = Intent(this@SignInActivity, MainActivity::class.java)
//            startActivity(intent)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == signInID) {
            signInViewModel.signInWithGoogle(data)
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuth(account)
//            } catch (e: ApiException) {
//                Log.w(TAG, "Google sign in failed: ", e)
//            }
        }
    }

//    private fun firebaseAuth(acct: GoogleSignInAccount?) {
//        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    user?.getIdToken(true)?.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val idToken = task.result?.token
//                            Log.d(TAG, "Token Firebase: $idToken")
//                        } else {
//                            Log.w(TAG, "Failed to retrieve token", task.exception)
//                        }
//                    }
//                    print(user)
//                    updateUI(user)
//                } else {
//                    Log.w(TAG, "SignIn with credential:failure", task.exception)
//                    showToast("Authentication failed")
//                    updateUI(null)
//                }
//            }
//    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG = SignInActivity::class.java.simpleName
    }
}