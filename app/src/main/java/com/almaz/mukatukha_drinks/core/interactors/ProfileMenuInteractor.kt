package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.ProfileMenuRepository
import com.almaz.mukatukha_drinks.core.model.Fact
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileMenuInteractor
@Inject constructor(
    private val profileMenuRepository: ProfileMenuRepository
) {

    fun getFactsList(): Single<List<Fact>> =
        profileMenuRepository.getFactsList()
            .subscribeOn(Schedulers.io())
}
