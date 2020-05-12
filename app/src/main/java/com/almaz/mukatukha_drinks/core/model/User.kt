package com.almaz.mukatukha_drinks.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Long = -1,
    val name: String?,
    val phoneNumber: String?,
    val email: String?,
    val discountPoints: Int = 0
) : Parcelable