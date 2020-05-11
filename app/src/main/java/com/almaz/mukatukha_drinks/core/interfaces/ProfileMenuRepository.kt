package com.almaz.mukatukha_drinks.core.interfaces

import com.almaz.mukatukha_drinks.core.model.Fact
import io.reactivex.Single

interface ProfileMenuRepository {
    fun getFactsList(): Single<List<Fact>>
}