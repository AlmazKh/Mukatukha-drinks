package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Single

interface MenuRepository {
    fun getProductList(productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>>
}