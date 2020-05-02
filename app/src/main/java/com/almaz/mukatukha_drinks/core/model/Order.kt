package com.almaz.mukatukha_drinks.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order (
    val id: String,
    val secretCode: String,
    val time: String,
    val cafe: Cafe,
    val products: Map<Product, Int>,
    val totalCost: Float
) : Parcelable