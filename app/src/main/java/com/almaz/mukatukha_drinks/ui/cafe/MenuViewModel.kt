package com.almaz.mukatukha_drinks.ui.cafe

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.MenuInteractor
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MenuViewModel
@Inject constructor(
    private val menuInteractor: MenuInteractor
) : BaseViewModel() {

    val productListLiveData = MutableLiveData<Response<List<Product>>>()
    val productClickLiveData = MutableLiveData<Response<Product>>()

    fun updateProductList(productCategory: ProductCategory, withMilk: Boolean) {
        showLoadingLiveData.value = true
        disposables.add(
            menuInteractor.getProductList(productCategory, withMilk)
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
    }

    fun onProductClick(product: Product) {
        productClickLiveData.value = Response.success(product)
    }

    fun onAddProductClick(product: Product) {
        disposables.add(
            menuInteractor.addProductIntoBasket(product)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // TODO add into basket resp
                }, {

                })
        )
    }

    fun onRemoveProductClick(product: Product) {
        disposables.add(
            menuInteractor.removeProductFromBasket(product)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // TODO delete from basket resp
                }, {

                })
        )
    }
}