package com.almaz.mukatukha_drinks.core.model

data class OrderRequest(
    val phoneNumber: String,
    val paymentMethod: String,
    val products: Map<Product, Int>,
    val totalCost: Double,
    val ownerId: Long,
    val promocode: String? = null
)