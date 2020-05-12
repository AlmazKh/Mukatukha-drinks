package com.almaz.mukatukha_drinks.core.model.remote

import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.core.model.Product

//TODO need to fix
data class OrderRemote(
    val id: String,
    val secretCode: String,
    val time: String,
    val cafe: Cafe,
    val products: Map<Product, Int>,
    val totalCost: Double
)