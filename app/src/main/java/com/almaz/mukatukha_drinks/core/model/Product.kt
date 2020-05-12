package com.almaz.mukatukha_drinks.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val volume: String,
    val category: ProductCategory?,
    val withMilk: Boolean?,
    val otherDetails: String?
): Parcelable