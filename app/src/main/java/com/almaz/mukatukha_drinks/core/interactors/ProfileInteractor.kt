package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileInteractor
@Inject constructor(
    private val userRepository: UserRepository
){
    fun checkAuthUser(): Single<Boolean> =
        userRepository.checkAuthUser()
            .subscribeOn(Schedulers.io())
}