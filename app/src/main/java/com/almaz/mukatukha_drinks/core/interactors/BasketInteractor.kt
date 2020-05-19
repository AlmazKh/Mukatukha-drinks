package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.Order
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BasketInteractor
@Inject constructor(
    private val basketRepository: BasketRepository,
    private val userRepository: UserRepository
) {

    fun updateBasketProductList(): Single<List<Basket>> =
        basketRepository.getBasketProductList()
            .subscribeOn(Schedulers.io())

    fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?
    ): Single<Order> =
        basketRepository.getBasketProductList()
            .flatMap { basket ->
                userRepository.getCurrentUser()
                    .flatMap {
                        basketRepository.makeOrder(
                            phoneNumber,
                            paymentMethod,
                            promocode,
                            basket,
                            it
                        )
                    }
            }
            .subscribeOn(Schedulers.io())

    fun checkHasActiveOrder(): Single<Boolean> =
        userRepository.getCurrentUser()
            .flatMap {
                basketRepository.checkHasActiveOrder(it)
            }
            .subscribeOn(Schedulers.io())

    fun checkHasProductsInBasket(): Single<Boolean> =
        basketRepository.checkHasProductsInBasket()
            .subscribeOn(Schedulers.io())
}
