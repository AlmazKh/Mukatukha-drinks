package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.CafeRepository
import com.almaz.mukatukha_drinks.core.model.Cafe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CafeInteractor
@Inject constructor(
    private val cafeRepository: CafeRepository
) {

    fun getCafeList(): Single<List<Cafe>> =
        cafeRepository.getCafeList()
            .subscribeOn(Schedulers.io())
}
