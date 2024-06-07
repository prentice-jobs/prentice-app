package com.prenticedev.prenticeapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentModel(
    val avatar: Int,
    val name: String,
    val comment: String
):Parcelable