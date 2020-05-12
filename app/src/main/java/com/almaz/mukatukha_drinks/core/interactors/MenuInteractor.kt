package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuInteractor
@Inject constructor(
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository
){

    fun getProductList(cafeId: String, productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>> =
        menuRepository.getProductList(cafeId, productCategory, withMilk)
            .subscribeOn(Schedulers.io())

    fun addProductIntoBasket(product: Product): Completable =
        userRepository.getCurrentUser()
            .flatMapCompletable {
                menuRepository.addProductIntoBasket(product, it)
            }
            .subscribeOn(Schedulers.io())

    fun removeProductFromBasket(product: Product): Completable =
        userRepository.getCurrentUser()
            .flatMapCompletable {
                menuRepository.removeProductFromBasket(product, it)
            }
            .subscribeOn(Schedulers.io())
}