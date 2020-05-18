package com.almaz.mukatukha_drinks.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRemote(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("discountPoints")
    @Expose
    val discountPoints: Int
)