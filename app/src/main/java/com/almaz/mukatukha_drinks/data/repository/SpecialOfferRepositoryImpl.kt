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
                        "Surf Coffee x Muses",
                        "ул.Пушкина, д.20",
                        "8-800-555-35-35",
                        "15 мин.",
                        null
                    ),
                    "Скидка 20% по промокоду MUKATUKHA"
                ),
                Offer(
                    Cafe(
                        "2",
                        "Ранняя пташка",
                        "ул.Баумана, д.32",
                        "8-800-555-35-35",
                        "7 мин.",
                        null
                    ),
                    "Возвращаем 50% от заказа баллами"
                ),
                Offer(
                    Cafe(
                        "2",
                        "Surf Coffee x Muses",
                        "ул.Баумана, д.32",
                        "8-800-555-35-35",
                        "7 мин.",
                        null
                    ),
                    "Скидка 100 руб. при заказе от 4 чашек по промокоду MUKA2020"
                ),
                Offer(
                    Cafe(
                        "2",
                        "Нефть",
                        "ул.Баумана, д.32",
                        "8-800-555-35-35",
                        "7 мин.",
                        null
                    ),
                    "Чашка кофе за барель нефти"
                ),
                Offer(
                    Cafe(
                        "2",
                        "Skuratov Coffee Roasters",
                        "ул.Баумана, д.32",
                        "8-800-555-35-35",
                        "7 мин.",
                        null
                    ),
                    "Скидка 100% на каждый 5 заказ"
                )
            )
        )
    }
}