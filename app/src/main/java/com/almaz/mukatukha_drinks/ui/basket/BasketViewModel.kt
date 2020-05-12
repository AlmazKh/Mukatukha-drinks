package com.almaz.mukatukha_drinks.ui.basket

import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.BasketInteractor
import com.almaz.mukatukha_drinks.core.interactors.MenuInteractor
import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.Order
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.BasketState
import com.almaz.mukatukha_drinks.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BasketViewModel
@Inject constructor(
    private val menuInteractor: MenuInteractor,
    private val basketInteractor: BasketInteractor
) : BaseViewModel() {

    val basketState = MutableLiveData<BasketState>()
    val orderStatusLiveData = MutableLiveData<Response<Order>>()
    val productListLiveData = MutableLiveData<Response<List<Basket>>>()
    val productClickLiveData = MutableLiveData<Response<Product>>()

    fun updateBasketProductList() {
        showLoadingLiveData.value = true
        disposables.add(
            basketInteractor.updateBasketProductList()
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

    fun makeOrder(
        phoneNumber: String,
        paymentMethod: String,
        promocode: String?
    ) {
        disposables.add(
            basketInteractor.makeOrder(
                phoneNumber,
                paymentMethod,
                promocode
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    basketState.value = BasketState.HAS_ACTIVE_ORDER
                    orderStatusLiveData.value = Response.success(it)
                }, {
                    orderStatusLiveData.value = Response.error(it)
                    it.printStackTrace()
                })
        )
    }

    fun checkBasketState() {
        disposables.add(
            basketInteractor.checkHasActiveOrder()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        basketState.value = BasketState.HAS_ACTIVE_ORDER
                    } else {
                        basketInteractor.checkHasProductsInBasket()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if (it) {
                                    basketState.value = BasketState.NOT_EMPTY
                                } else {
                                    basketState.value = BasketState.EMPTY
                                }
                            }, { error ->
                                error.printStackTrace()
                            })
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }
}