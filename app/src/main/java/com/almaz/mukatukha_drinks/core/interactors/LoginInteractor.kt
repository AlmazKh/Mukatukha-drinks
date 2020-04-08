package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginInteractor
@Inject constructor(
        private val userRepository: UserRepository
) {
    fun loginWithGoogle(acct: GoogleSignInAccount): Completable =
            userRepository.loginWithGoogle(acct)
                    .subscribeOn(Schedulers.io())

    fun loginWithPhone(storedVerificationId: String, verificationCode: String, userName: String, phone: String): Completable {
        return userRepository.loginWithPhone(storedVerificationId, verificationCode, userName, phone)
            .subscribeOn(Schedulers.io())
    }

    fun sendVerificationCode(phoneNumber: String): Maybe<String> =
        userRepository.sendVerificationCode(phoneNumber)
            .subscribeOn(Schedulers.io())
}
