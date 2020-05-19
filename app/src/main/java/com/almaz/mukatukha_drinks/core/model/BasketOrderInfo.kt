package com.almaz.mukatukha_drinks.core.model

data class BasketOrderInfo(
    val totalSum: Double,
    val basket: List<Basket>
)