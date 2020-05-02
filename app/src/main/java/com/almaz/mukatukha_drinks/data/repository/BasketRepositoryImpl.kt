package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.BasketRepository
import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.core.model.db.BasketDB
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Single
import javax.inject.Inject

class BasketRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao
) : BasketRepository {

    override fun getBasketProductList(): Single<List<Pair<Product, Int>>> {
        return basketDao.getItemsFromBasket()
            .flatMap {
                getProductInfoById(it)
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
                        "150 руб.",
                        "0.5 л",
                        ProductCategory.OTHER_DRINKS,
                        true,
                        "Some text with other description in two lines may be"
                    ) to 5),
                    758F
                )
            )
        }
    }

    private fun getProductInfoById(basket: List<BasketDB>): Single<List<Pair<Product, Int>>> {
        //TODO
        return Single.create { emitter ->
            val list: MutableList<Pair<Product, Int>> = mutableListOf()
            for (item in basket) {
                //list.add(api.getProdInfoById(item.productId) to item.amount)
                list.add(
                    Product(
                        "5",
                        "Молочный коктейль",
                        "190 руб.",
                        "0.3 л",
                        ProductCategory.OTHER_DRINKS,
                        true,
                        "Some text with description"
                    ) to item.amount
                )
            }
            emitter.onSuccess(list)
        }
    }
}
