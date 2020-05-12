package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import io.reactivex.Single

interface BasketRepository {
    fun getBasketProductList(): Single<List<Basket>>
    fun makeOrder(phoneNumber: String): Single<Order>
}
