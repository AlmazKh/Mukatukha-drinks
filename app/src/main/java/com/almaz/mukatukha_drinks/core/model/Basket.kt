package com.almaz.mukatukha_drinks.core.model

data class Basket(
    val id: Long,
    val amount: Int,
    val ownerId: Long,
    val product: Product
)