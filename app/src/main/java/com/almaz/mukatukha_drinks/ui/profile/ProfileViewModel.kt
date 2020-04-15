package com.almaz.mukatukha_drinks.ui.profile

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.ProfileInteractor
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProfileViewModel
    @Inject constructor(
        private val profileInteractor: ProfileInteractor
    ): BaseViewModel() {

    val isLoginedLiveData = MutableLiveData<Response<Boolean>>()

    fun checkAuthUser() {
        disposables.add(
            profileInteractor.checkAuthUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        isLoginedLiveData.value = Response.success(true)
                    } else {
                        isLoginedLiveData.value = Response.success(false)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun logout() {
        disposables.add(
            profileInteractor.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoginedLiveData.value = Response.success(false)
                }, {
                    isLoginedLiveData.value = Response.error(it)
                    it.printStackTrace()
                })
        )
    }
}
