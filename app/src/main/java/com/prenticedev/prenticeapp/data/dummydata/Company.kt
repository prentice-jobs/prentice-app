package com.prenticedev.prenticeapp.data.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val name: String,
    val location: String,
    val postDate: String,
    val reviewTitle: String,
    val reviewContent: String
): Parcelable