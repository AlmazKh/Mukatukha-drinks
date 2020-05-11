package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.ProfileMenuRepository
import com.almaz.mukatukha_drinks.core.model.Fact
import io.reactivex.Single
import javax.inject.Inject

class ProfileMenuRepositoryImpl
@Inject constructor() : ProfileMenuRepository {

    override fun getFactsList(): Single<List<Fact>> {
        return Single.just(
            listOf(
                Fact(
                    "1",
                    "Fact1",
                    "asdasfa asdasf asfasfa asfa"
                ),
                Fact(
                    "2",
                    "Fact2",
                    "asdasfa asdasf asfasfa asfa"
                ),
                Fact(
                    "3",
                    "Fact3",
                    "asdasfa asdasf asfasfa asfa"
                )
            )
        )
    }

}