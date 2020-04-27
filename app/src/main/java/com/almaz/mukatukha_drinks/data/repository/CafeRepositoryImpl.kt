package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.CafeRepository
import com.almaz.mukatukha_drinks.core.model.Cafe
import io.reactivex.Single
import javax.inject.Inject

class CafeRepositoryImpl
@Inject constructor() : CafeRepository {

    override fun getCafeList(): Single<List<Cafe>> {
        return Single.just(listOf(
            Cafe(
                "1",
                "Coffee for U 1",
                "ул.Пушкина, д.20",
                "8-800-555-35-35",
                "15 мин.",
                null
            ),
            Cafe(
                "2",
                "Some coffee shop 2",
                "ул.Баумана, д.32",
                "8-800-555-35-35",
                "7 мин.",
                null
            )
        ))
    }
}