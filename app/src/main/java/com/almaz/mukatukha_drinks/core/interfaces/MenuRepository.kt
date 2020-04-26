package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Product
import io.reactivex.Single

interface MenuRepository {
    fun getProductList(): Single<List<Product>>
}