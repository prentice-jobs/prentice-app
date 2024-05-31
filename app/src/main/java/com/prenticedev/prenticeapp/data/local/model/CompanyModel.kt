package com.prenticedev.prenticeapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyModel(
    val companyName: String,
    val companyCategory: String,
    val companyRating:String,
    val logo: Int
):Parcelable