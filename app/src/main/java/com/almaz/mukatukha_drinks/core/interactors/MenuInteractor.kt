package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuInteractor
@Inject constructor(
    private val menuRepository: MenuRepository
){

    fun getProductList(): Single<List<Product>> =
        menuRepository.getProductList()
            .subscribeOn(Schedulers.io())
}