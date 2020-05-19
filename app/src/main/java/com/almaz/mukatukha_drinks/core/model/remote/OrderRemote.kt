package com.almaz.mukatukha_drinks.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderRemote(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("secretCode")
    @Expose
    val secretCode: String,
    @SerializedName("buyTime")
    @Expose
    val buyTime: String,
    @SerializedName("readyTime")
    @Expose
    val readyTime: String,
    @SerializedName("cafe")
    @Expose
    val cafe: CafeRemote,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("totalSum")
    @Expose
    val totalSum: Double,
    @SerializedName("products")
    @Expose
    val products: List<ProductRemote>?
)