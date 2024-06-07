package com.prenticedev.prenticeapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(
    val userPhoto: Int,
    val userName: String,
    val location: String,
    val role: String,
    val status: String,
    val reviewTitle: String,
    val reviewContent: String
):Parcelable
