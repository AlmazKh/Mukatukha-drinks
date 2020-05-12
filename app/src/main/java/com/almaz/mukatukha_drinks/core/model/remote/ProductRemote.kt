package com.almaz.mukatukha_drinks.core.model.remote

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ProductRemote(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("image")
    @Expose
    var image: Any? = null,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("otherDetails")
    @Expose
    var otherDetails: String? = null,
    @SerializedName("price")
    @Expose
    var price: Double,
    @SerializedName("category")
    @Expose
    var category: String,
    @SerializedName("withMilk")
    @Expose
    var withMilk: Boolean
)