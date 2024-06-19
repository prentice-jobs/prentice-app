package com.prenticedev.prenticeapp.ui.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.prenticedev.prenticeapp.data.local.pref.UserModel
import com.prenticedev.prenticeapp.data.remote.response.deployed.EmailRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.RegisterRequest
import com.prenticedev.prenticeapp.data.remote.response.deployed.RegisterResponse
import com.prenticedev.prenticeapp.data.remote.retrofit.ApiConfig
import com.prenticedev.prenticeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class SignInViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    private val _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> = _authState

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _isUserExist = MutableLiveData<Boolean>()
    val isUserExist: LiveData<Boolean> = _isUserExist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    fun signInWithGoogle(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val acc = task.getResult(ApiException::class.java)
            firebaseAuth(acc)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    fun saveUserToken(userData: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(userData)
        }
    }


    private fun firebaseAuth(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        _isLoading.value = true
        auth.signInWithCredential(credential)
            .addOnCompleteListener { it ->
                _isLoading.value = false
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    checkUserExist(user?.email.toString()) //MEMANGGIL API UNTUK MELAKUKAN PENGECEKAN APAKAH USER TELAH TERDAFTAR SEBELUMNYA

                    if (_isUserExist.value != true) { //Logic untuk register user yang masih belum terdaftar
                        registerUser(
                            user?.uid.toString(),
                            user?.email.toString(),
                            user?.displayName.toString(),
                            user?.photoUrl.toString(),
                            user?.isEmailVerified.toString()
                        )
                    }
                    user?.getIdToken(true)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val idToken = it.result.token
                            viewModelScope.launch {
                                userRepository.saveSession(
                                    UserModel(
                                        it.result.token.toString(),
                                        true
                                    )
                                )
                            }
                            Log.d(TAG, "Token Firebase: ${idToken.toString()}")
                        } else {
                            Log.e(TAG, Log.ERROR.toString())
                        }
                    }
                    _authState.value = user
                } else {
                    Log.w(TAG, "Sign In Fail", it.exception)
                    _authState.value = null
                }
            }
    }

    private fun checkUserExist(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getApiService()
                val response: Response<Boolean> = withContext(Dispatchers.IO) {
                    apiService.checkUserIsExist(EmailRequest(email)).execute()
                }
                _isUserExist.value = response.body()

            } catch (e: HttpException) {
                Log.e(TAG, e.message().toString())
                _isUserExist.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun registerUser(
        firebaseUID: String,
        email: String,
        displayName: String,
        photoUrl: String,
        emailVerified: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getApiService()
                val registerRequest = RegisterRequest(
                    firebaseUID = firebaseUID,
                    email = email,
                    displayName = displayName,
                    photoUrl = photoUrl,
                    emailVerified = emailVerified
                )
                val response: Response<RegisterResponse> = withContext(Dispatchers.IO) {
                    apiService.registerUser(
                        registerRequest
                    ).execute()
                }
                if (response.isSuccessful) {
                    _registerResponse.value = response.body()
                    Log.d(TAG, "New User is Registered! ${_registerResponse.value}")
                } else {
                    Log.e(TAG, "Registration Failed! ${response.errorBody().toString()}")
                }
            } catch (e: HttpException) {
                Log.e(TAG, e.message())
            } finally {
                _isLoading.value = false
            }
        }


    }


    companion object {
        private val TAG = SignInViewModel::class.java.simpleName
    }
}