package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.SpecialOfferRepository
import com.almaz.mukatukha_drinks.core.model.Offer
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SpecialOfferInteractor
@Inject constructor(
    private val specialOfferRepository: SpecialOfferRepository
) {

    fun getOffersList(): Single<List<Offer>> =
        specialOfferRepository.getOffersList()
            .subscribeOn(Schedulers.io())
}
