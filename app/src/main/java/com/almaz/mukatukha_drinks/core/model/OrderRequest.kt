package com.almaz.mukatukha_drinks.core.model

data class OrderRequest(
    val phoneNumber: String,
    val paymentMethod: String,
    val products: Map<Product, Int>,
    val totalCost: Double,
    val cafe: Cafe,
    val customer: User,
    val promocode: String? = null
)