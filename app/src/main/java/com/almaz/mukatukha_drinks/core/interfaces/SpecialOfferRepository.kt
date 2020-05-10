package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Offer
import io.reactivex.Single

interface SpecialOfferRepository {
    fun getOffersList(): Single<List<Offer>>
}
