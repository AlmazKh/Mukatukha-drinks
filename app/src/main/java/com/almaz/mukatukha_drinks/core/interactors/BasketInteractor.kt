package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BasketInteractor
@Inject constructor(
    private val basketRepository: BasketRepository
) {

    fun updateBasketProductList(): Single<List<Basket>> =
        basketRepository.getBasketProductList()
            .subscribeOn(Schedulers.io())

    fun makeOrder(phoneNumber: String): Single<Order> =
        basketRepository.makeOrder(phoneNumber)
            .subscribeOn(Schedulers.io())
}
