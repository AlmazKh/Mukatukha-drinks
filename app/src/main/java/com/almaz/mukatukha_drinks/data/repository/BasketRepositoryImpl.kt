package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.model.*
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import com.almaz.mukatukha_drinks.core.model.remote.OrderRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Single
import javax.inject.Inject

class BasketRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao,
    private val api: MukatukhaAPI
) : BasketRepository {

    override fun getBasketProductList(): Single<BasketOrderInfo> {
        return basketDao.getItemsFromBasket().map {
            mapBasketAndProductToLocalBasket(it)
        }
            .map {
                BasketOrderInfo(
                    countTotalCost(it),
                    it
                )
            }
    }

    /*override fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?,
        basket: List<Basket>,
        owner: User
    ): Single<Order> {
        return Single.fromObservable(
            api.makeOrder(
                OrderRequest(
                    phoneNumber = phoneNumber,
                    paymentMethod = paymentMethod,
                    products = mapBasketToProductAmountMap(basket),
                    totalCost = countTotalCost(basket),
                    cafe = Cafe("1", "1", "1", "1", "1", "1"),
                    customer = owner,
                    promocode = promocode
                )
            )
        )
            .map {
                mapResponseOrderToLocal(it)
            }

    }*/

    override fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?,
        basket: List<Basket>,
        owner: User
    ): Single<Order> {
        return Single.just(
            Order(
                id = "1",
                        secretCode = "F-829",
                        time = "7:29",
                    cafe = Cafe("1", "Skuratov Coffee Roasters", "1", "1", "1", "1"),
                        products = mapOf<Product, Int>(
                            Product(
                                "1",
                                "Американо",
                                100.0,
                                "0.3 л",
                                ProductCategory.COFFEE,
                                false,
                                "Some text with description"
                            )to 2,
                            Product(
                                "1",
                                "Капучино",
                                100.0,
                                "0.3 л",
                                ProductCategory.COFFEE,
                                false,
                                "Some text with description"
                            ) to 1
                        ),
                totalCost = 720.0

            )
        )

    }

    override fun checkHasActiveOrder(user: User): Single<Boolean> {
        return Single.fromObservable(api.checkHasActiveOrder(user.id!!))
    }

    override fun checkHasProductsInBasket(): Single<Boolean> {
        return basketDao.getItemsFromBasket()
            .map {
                it.isNotEmpty()
            }
    }

    private fun mapResponseOrderToLocal(order: OrderRemote): Order =
        Order(
            order.id.toString(),
            order.secretCode,
            order.buyTime,
            Cafe("1", "1", "1", "1", "1", "1"),
            mapOf(),
            order.totalSum
        )

    private fun mapBasketToProductAmountMap(basket: List<Basket>): Map<Product, Int> {
        val map = mutableMapOf<Product, Int>()
        for (item in basket) {
            map[item.product] = item.amount
        }
        return map
    }

    private fun countTotalCost(basket: List<Basket>): Double {
        var totalCost = 0.0
        for (item in basket) {
            totalCost += item.product.price * item.amount
        }
        return totalCost
    }

    private fun mapBasketAndProductToLocalBasket(basketAndProduct: List<BasketAndProduct>): List<Basket> {
        val list = mutableListOf<Basket>()
        for (item in basketAndProduct) {
            list.add(
                Basket(
                    item.basket.id,
                    item.basket.amount,
                    item.basket.ownerId,
                    Product(
                        item.product.id.toString(),
                        item.product.name,
                        item.product.price,
                        item.product.volume,
                        null,
                        null,
                        item.product.otherDetails
                    )
                )
            )
        }
        return list
    }
}
