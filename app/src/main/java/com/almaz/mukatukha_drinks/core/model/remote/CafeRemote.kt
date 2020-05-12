package com.almaz.mukatukha_drinks.core.model.remote

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class CafeRemote(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("address")
    @Expose
    val address: String,
    @SerializedName("otherDetails")
    @Expose
    val otherDetails: String? = null
)