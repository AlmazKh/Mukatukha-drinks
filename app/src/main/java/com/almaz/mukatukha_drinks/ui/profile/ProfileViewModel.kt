package com.almaz.mukatukha_drinks.ui.profile

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.ProfileInteractor
import com.almaz.mukatukha_drinks.core.model.User
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.AuthenticationState
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProfileViewModel
    @Inject constructor(
        private val profileInteractor: ProfileInteractor
    ): BaseViewModel() {

    val authenticationState  = MutableLiveData<AuthenticationState>()
    val userDataLiveData = MutableLiveData<Response<User>>()

    init {
        checkAuthUser()
    }

    fun checkAuthUser() {
        disposables.add(
            profileInteractor.checkAuthUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        authenticationState.value = AuthenticationState.AUTHENTICATED
                    } else {
                        authenticationState.value = AuthenticationState.UNAUTHENTICATED
                    }
                }, {
                    it.printStackTrace()
                    authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                })
        )
    }

    fun logout() {
        disposables.add(
            profileInteractor.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    authenticationState.value = AuthenticationState.UNAUTHENTICATED
                }, {
                    authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                    it.printStackTrace()
                })
        )
    }

    fun updateUserInfo() {
        disposables.add(
            profileInteractor.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userDataLiveData.value = Response.success(it)
                }, {
                    userDataLiveData.value = Response.error(it)
                    it.printStackTrace()
                })
        )
    }
}
