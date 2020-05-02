package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.Product
import io.reactivex.Single

interface BasketRepository {
    fun getBasketProductList(): Single<List<Pair<Product, Int>>>
    fun makeOrder(phoneNumber: String): Single<Order>
}
