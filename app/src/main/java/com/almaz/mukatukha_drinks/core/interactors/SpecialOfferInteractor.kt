package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.SpecialOfferRepository
import javax.inject.Inject

class SpecialOfferInteractor
@Inject constructor(
    private val specialOfferRepository: SpecialOfferRepository
) {
}