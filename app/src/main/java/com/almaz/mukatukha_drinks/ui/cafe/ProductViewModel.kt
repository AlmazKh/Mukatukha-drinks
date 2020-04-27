package com.almaz.mukatukha_drinks.ui.cafe

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.MenuInteractor
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProductViewModel
@Inject constructor(
    private val menuInteractor: MenuInteractor
) : BaseViewModel() {

    val productClickLiveData = MutableLiveData<Response<Product>>()

   /* fun updateProductList() {
        showLoadingLiveData.value = true
        disposables.add(
            menuInteractor.getProductList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    productListLiveData.value = Response.success(it)
                }, { error ->
                    productListLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }*/

    fun onProductClick(product: Product) {
        productClickLiveData.value = Response.success(product)
    }
}