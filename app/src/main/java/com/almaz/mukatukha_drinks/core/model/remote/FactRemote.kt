package com.almaz.mukatukha_drinks.core.model.remote

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class FactRemote(
    @SerializedName("id")
    @Expose
    var id: Long,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("text")
    @Expose
    var text: String
)