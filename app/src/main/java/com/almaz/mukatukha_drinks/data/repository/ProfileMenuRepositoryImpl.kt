package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.ProfileMenuRepository
import com.almaz.mukatukha_drinks.core.model.Fact
import com.almaz.mukatukha_drinks.core.model.remote.FactRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import io.reactivex.Single
import javax.inject.Inject

class ProfileMenuRepositoryImpl
@Inject constructor(
    private val api: MukatukhaAPI
) : ProfileMenuRepository {

    override fun getFactsList(): Single<List<Fact>> {
        return Single.fromObservable(
            api.getFacts()
        ).map {
            mapRemoteFactListToLocal(it)
        }
    }

    private fun mapRemoteFactListToLocal(remote: List<FactRemote>): List<Fact> {
        val list = mutableListOf<Fact>()
        for (elt in remote) {
            list.add(
                Fact(
                    id = elt.id,
                    title = elt.title,
                    description = elt.text
                )
            )
        }
        return list
    }
}
