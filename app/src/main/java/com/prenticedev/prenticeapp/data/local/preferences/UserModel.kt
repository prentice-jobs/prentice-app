package com.prenticedev.prenticeapp.data.local.preferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String? = null,
    var email: String? = null,
    var avatar: String? = null
) : Parcelable
