package com.prenticedev.prenticeapp.data.helper

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.prenticedev.prenticeapp.data.local.pref.UserModel
import com.prenticedev.prenticeapp.data.local.pref.UserPreference
import com.prenticedev.prenticeapp.data.local.pref.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRefreshWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            try {
                val result: GetTokenResult = Tasks.await(currentUser.getIdToken(true))
                val newToken = result.token
                Log.d(TAG, "NEW TOKEN HAS GENERATED: $newToken")

                newToken?.let {
                    val userPreference = UserPreference.getInstance(applicationContext.dataStore)
                    userPreference.saveSession(UserModel(it, true))
                }
                Result.success()
            } catch (e: Exception) {
                Log.e(TAG, "REFRESH TOKEN HAS FAILED: ${e.message.toString()}")
                Result.retry()
            }
        } else {
            Result.failure()
        }
    }

    companion object {
        private val TAG = TokenRefreshWorker::class.java.simpleName
    }
}