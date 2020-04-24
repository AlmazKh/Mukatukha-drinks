package com.almaz.mukatukha_drinks.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cafe(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val averageCookingTime: String,
    val otherDetails: String?
) : Parcelable