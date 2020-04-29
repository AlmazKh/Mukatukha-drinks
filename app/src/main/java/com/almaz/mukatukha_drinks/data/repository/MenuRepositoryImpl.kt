package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.core.model.db.BasketDB
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao
) : MenuRepository {

    override fun getProductList(productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>> {
        return when(productCategory){
            ProductCategory.COFFEE -> {
                if (withMilk) {
                    Single.just(listOf(
                        Product(
                            "1",
                            "Латте",
                            "100 руб.",
                            "0.3 л",
                            ProductCategory.COFFEE,
                            true,
                            "Some text with description"
                        ),
                        Product(
                            "2",
                            "Капучино",
                            "150 руб.",
                            "0.5 л",
                            ProductCategory.COFFEE,
                            true,
                            "Some text with other description in two lines may be"
                        )
                    ))
                } else {
                    Single.just(listOf(
                        Product(
                            "3",
                            "Американо",
                            "100 руб.",
                            "0.3 л",
                            ProductCategory.COFFEE,
                            false,
                            "Some text with description"
                        ),
                        Product(
                            "4",
                            "Эспрессо",
                            "150 руб.",
                            "0.5 л",
                            ProductCategory.COFFEE,
                            false,
                            "Some text with other description in two lines may be"
                        )
                    ))
                }

            }
            ProductCategory.OTHER_DRINKS -> {
                if (withMilk) {
                    Single.just(listOf(
                        Product(
                            "5",
                            "Молочный коктейль",
                            "190 руб.",
                            "0.3 л",
                            ProductCategory.OTHER_DRINKS,
                            true,
                            "Some text with description"
                        ),
                        Product(
                            "6",
                            "Чай с молоком",
                            "150 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            true,
                            "Some text with other description in two lines may be"
                        )
                    ))
                } else {
                    Single.just(listOf(
                        Product(
                            "7",
                            "Апельсиновый сок",
                            "500 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            false,
                            "Some text with description"
                        ),
                        Product(
                            "8",
                            "Морковный фреш",
                            "250 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            false,
                            "Some text with other description in two lines may be"
                        )
                    ))
                }
            }
        }
    }

    override fun addProductIntoBasket(product: Product): Completable {
        return Completable.create { emitter ->
            val amount = basketDao.getProductAmountById(product.id.toLong())
            if (amount != null) {
                basketDao.updateProductAmount(
                    product.id.toLong(),
                    amount + 1,
                    1 // TODO setting current user id
                )
            } else {
                basketDao.insertItemIntoBasket(
                    BasketDB(
                        productId = product.id.toLong(),
                        amount = 1,
                        ownerId = 1 // TODO setting current user id
                    )
                )
            }
            emitter.onComplete()
        }
    }

    override fun removeProductFromBasket(product: Product): Completable {
        return Completable.create { emitter ->
            val amount = basketDao.getProductAmountById(product.id.toLong())
            if (amount != null) {
                if (amount == 1) {
                    basketDao.delete(
                        BasketDB(
                            productId = product.id.toLong(),
                            amount = 1,
                            ownerId = 1
                        )
                    )
                } else {
                    basketDao.updateProductAmount(
                        product.id.toLong(),
                        amount - 1,
                        1 // TODO setting current user id
                    )
                }
            }
            emitter.onComplete()
        }
    }
}