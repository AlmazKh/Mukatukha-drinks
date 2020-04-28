package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuInteractor
@Inject constructor(
    private val menuRepository: MenuRepository
){

    fun getProductList(productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>> =
        menuRepository.getProductList(productCategory, withMilk)
            .subscribeOn(Schedulers.io())

    fun addProductIntoBasket(product: Product): Completable = Completable.complete()

    fun removeProductFromBasket(product: Product): Completable = Completable.complete()
}