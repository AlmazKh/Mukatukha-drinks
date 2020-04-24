package com.almaz.mukatukha_drinks.ui.cafe

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.CafeInteractor
import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CafeListViewModel
    @Inject constructor(
        private val cafeInteractor: CafeInteractor
    ) : BaseViewModel() {
    val cafeListLiveData = MutableLiveData<Response<List<Cafe>>>()
    val cabinetClickLiveData = MutableLiveData<Response<Cafe>>()

    fun updateCafeList() {
        showLoadingLiveData.value = true
        disposables.add(
            cafeInteractor.getCafeList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    cafeListLiveData.value = Response.success(it)
                }, { error ->
                    cafeListLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }

    fun onCafeClick(cafe: Cafe) {
        cabinetClickLiveData.value = Response.success(cafe)
    }
}