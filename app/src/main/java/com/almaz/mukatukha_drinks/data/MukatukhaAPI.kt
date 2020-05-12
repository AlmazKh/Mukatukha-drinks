package com.almaz.mukatukha_drinks.data

import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.core.model.remote.CafeRemote
import com.almaz.mukatukha_drinks.core.model.remote.ProductRemote
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MukatukhaAPI {

    @GET("/cafes")
    fun getCafeList(): Observable<List<CafeRemote>>

    @GET("/products")
    fun getCafeMenu(
        @Query("cafe") cafeId: String,
        @Query("category") category: ProductCategory,
        @Query("withMilk") withMilk: Boolean
    ): Observable<List<ProductRemote>>
}