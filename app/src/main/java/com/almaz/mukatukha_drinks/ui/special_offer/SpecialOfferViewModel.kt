package com.almaz.mukatukha_drinks.ui.special_offer

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.SpecialOfferInteractor
import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SpecialOfferViewModel
@Inject constructor(
    private val specialOfferInteractor: SpecialOfferInteractor
) : BaseViewModel() {

    val offersLiveData = MutableLiveData<Response<Offer>>()

    fun updateOffersList() {
        showLoadingLiveData.value = true
        disposables.add(
            specialOfferInteractor.updateOffersList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    offersLiveData.value = Response.success(it)
                }, { error ->
                    offersLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }
}
