package com.almaz.mukatukha_drinks.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fact(
    val id: String,
    val title: String,
    val description: String
): Parcelable
