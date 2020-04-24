package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Cafe
import io.reactivex.Single

interface CafeRepository {
    fun getCafeList(): Single<List<Cafe>>
}
