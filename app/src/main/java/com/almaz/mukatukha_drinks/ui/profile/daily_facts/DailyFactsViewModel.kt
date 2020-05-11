package com.almaz.mukatukha_drinks.ui.profile.daily_facts

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.ProfileMenuInteractor
import com.almaz.mukatukha_drinks.core.model.Fact
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DailyFactsViewModel
@Inject constructor(
    private val profileMenuInteractor: ProfileMenuInteractor
) : BaseViewModel() {

    val factsLiveData = MutableLiveData<Response<List<Fact>>>()

    fun updateFactsList() {
        showLoadingLiveData.value = true
        disposables.add(
            profileMenuInteractor.getFactsList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    factsLiveData.value = Response.success(it)
                }, { error ->
                    factsLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }
}