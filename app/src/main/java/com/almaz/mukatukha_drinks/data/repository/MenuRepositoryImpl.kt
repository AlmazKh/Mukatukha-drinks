package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.core.model.User
import com.almaz.mukatukha_drinks.core.model.db.BasketDB
import com.almaz.mukatukha_drinks.core.model.db.ProductDB
import com.almaz.mukatukha_drinks.core.model.remote.ProductRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import com.almaz.mukatukha_drinks.data.db.BasketDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl
@Inject constructor(
    private val basketDao: BasketDao,
    private val api: MukatukhaAPI
) : MenuRepository {

    override fun getProductList(
        cafeId: String,
        productCategory: ProductCategory,
        withMilk: Boolean
    ): Single<List<Product>> {
        return Single.fromObservable(
            api.getCafeMenu(
                cafeId,
                productCategory,
                withMilk
            )
                .map {
                    mapRemoteMenuToLocal(it)
                }
        )
    }

    override fun addProductIntoBasket(product: Product, user: User): Completable {
        return Completable.create { emitter ->
            val amount = basketDao.getProductAmountById(product.id.toLong())
            if (amount != null) {
                basketDao.updateProductAmount(
                    product.id.toLong(),
                    amount + 1,
                    user.id
                )
            } else {
                basketDao.insertProduct(
                    ProductDB(
                        id = product.id.toLong(),
                        name = product.name,
                        price = product.price,
                        volume = product.volume,
                        otherDetails = product.otherDetails
                    )
                )
                basketDao.insertItemIntoBasket(
                    BasketDB(
                        amount = 1,
                        ownerId = user.id,
                        productId = product.id.toLong()
                    )
                )
            }
            emitter.onComplete()
        }
    }

    override fun removeProductFromBasket(product: Product, user: User): Completable {
        return Completable.create { emitter ->
            val amount = basketDao.getProductAmountById(product.id.toLong())
            if (amount != null) {
                if (amount == 1) {
                    basketDao.delete(
                        BasketDB(
                            amount = 1,
                            ownerId = user.id,
                            productId = product.id.toLong()
                        )
                    )
                } else {
                    basketDao.updateProductAmount(
                        product.id.toLong(),
                        amount - 1,
                        user.id
                    )
                }
            }
            emitter.onComplete()
        }
    }

    private fun mapRemoteMenuToLocal(remote: List<ProductRemote>): List<Product> {
        val list = mutableListOf<Product>()
        for (product in remote) {
            list.add(
                Product(
                    product.id.toString(),
                    product.name,
                    product.price,
                    "0.5 л.",
                    ProductCategory.valueOf(product.category),
                    product.withMilk,
                    product.otherDetails
                )
            )
        }

        return list
    }
}