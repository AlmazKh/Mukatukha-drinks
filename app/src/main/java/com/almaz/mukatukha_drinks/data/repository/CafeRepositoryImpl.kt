package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.CafeRepository
import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.core.model.remote.CafeRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import io.reactivex.Single
import javax.inject.Inject

class CafeRepositoryImpl
@Inject constructor(
    private val api: MukatukhaAPI
) : CafeRepository {

    override fun getCafeList(): Single<List<Cafe>> {
        return Single.fromObservable(
            api.getCafeList()
        )
            .map {
                mapRemoteCafeListToLocal(it)
            }
    }

    private fun mapRemoteCafeListToLocal(remote: List<CafeRemote>): List<Cafe> {
        val list = mutableListOf<Cafe>()
        for (cafe in remote) {
            list.add(
                Cafe(
                    cafe.id.toString(),
                    cafe.name,
                    cafe.address,
                    cafe.phone,
                "15 min",
                    cafe.otherDetails
                )
            )
        }

        return list
    }
}