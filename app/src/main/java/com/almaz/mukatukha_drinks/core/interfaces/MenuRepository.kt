package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Completable
import io.reactivex.Single

interface MenuRepository {
    fun getProductList(cafeId: String, productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>>
    fun addProductIntoBasket(product: Product): Completable
    fun removeProductFromBasket(product: Product): Completable
}