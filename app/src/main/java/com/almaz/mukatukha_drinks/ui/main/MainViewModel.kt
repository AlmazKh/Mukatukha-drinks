package com.almaz.mukatukha_drinks.ui.main

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.LoginInteractor
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(
        private val loginInteractor: LoginInteractor
) : BaseViewModel() {

    val isLoginedLiveData = MutableLiveData<Response<Boolean>>()

    fun checkAuthUser() {
        disposables.add(
                loginInteractor.checkAuthUser()
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
}