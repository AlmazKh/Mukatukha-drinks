package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.SpecialOfferRepository
import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.core.model.Offer
import io.reactivex.Single
import javax.inject.Inject

class SpecialOfferRepositoryImpl
@Inject constructor() : SpecialOfferRepository {
    override fun getOffersList(): Single<List<Offer>> {
        return Single.just(
            listOf(
                Offer(
                    Cafe(
                        "1",
                        "Coffee for U 1",
                        "ул.Пушкина, д.20",
                        "8-800-555-35-35",
                        "15 мин.",
                        null
                    ),
                    "asdafd"
                ),
                Offer(
                    Cafe(
                        "2",
                        "Some coffee shop 2",
                        "ул.Баумана, д.32",
                        "8-800-555-35-35",
                        "7 мин.",
                        null
                    ),
                    "asdasdasf"
                )
            )
        )
    }
}