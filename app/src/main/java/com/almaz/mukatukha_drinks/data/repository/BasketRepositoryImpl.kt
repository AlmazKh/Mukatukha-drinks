package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.model.*
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Single
import javax.inject.Inject

class BasketRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao
) : BasketRepository {

    override fun getBasketProductList(): Single<List<Basket>> {
        return basketDao.getItemsFromBasket().map {
            mapBasketAndProductToLocalBasket(it)
        }
    }

    override fun makeOrder(phoneNumber: String): Single<Order> {
        return Single.create { emitter ->
            emitter.onSuccess(
                Order(
                    "1",
                    "F-095",
                    "07:45",
                    Cafe(
                        "1",
                        "Coffee for U 1",
                        "ул.Пушкина, д.20",
                        "8-800-555-35-35",
                        "15 мин.",
                        null
                    ),
                    mapOf(Product(
                        "6",
                        "Чай с молоком",
                        150.0,
                        "0.5 л",
                        ProductCategory.OTHERS,
                        true,
                        "Some text with other description in two lines may be"
                    ) to 5),
                    758F
                )
            )
        }
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
