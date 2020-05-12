package com.almaz.mukatukha_drinks.data

import com.almaz.mukatukha_drinks.core.model.Cafe
import com.almaz.mukatukha_drinks.core.model.remote.CafeRemote
import io.reactivex.Observable
import retrofit2.http.GET

interface MukatukhaAPI {

    @GET("/cafes")
    fun getCafeList(): Observable<List<CafeRemote>>

}