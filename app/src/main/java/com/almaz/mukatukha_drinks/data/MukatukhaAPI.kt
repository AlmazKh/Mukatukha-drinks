package com.almaz.mukatukha_drinks.data

import com.almaz.mukatukha_drinks.core.model.OrderRequest
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.core.model.User
import com.almaz.mukatukha_drinks.core.model.remote.CafeRemote
import com.almaz.mukatukha_drinks.core.model.remote.OrderRemote
import com.almaz.mukatukha_drinks.core.model.remote.ProductRemote
import com.almaz.mukatukha_drinks.core.model.remote.UserRemote
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MukatukhaAPI {

    @GET("/cafes")
    fun getCafeList(): Observable<List<CafeRemote>>

    @GET("/cafes/{cafeId}/products/filtered")
    fun getCafeMenu(
        @Path("cafeId") cafeId: String,
        @Query("category") category: ProductCategory,
        @Query("withMilk") withMilk: Boolean
    ): Observable<List<ProductRemote>>

    @POST("/makeOrder")
    fun makeOrder(@Body orderRequest: OrderRequest): Observable<OrderRemote>

    @GET("/order/active")
    fun checkHasActiveOrder(@Body user: User): Single<Boolean>

    /*@GET("/emailuser/{email}")
    fun getUserByEmail(@Path("email") email: String): Single<UserRemote?>
*/
    @GET("/user/email")
    fun getUserByEmail(@Query("email") email: String): Observable<UserRemote?>

    @GET("/user/phone")
    fun getUserByPhone(@Path("phone") phone: String): Observable<UserRemote?>

    @POST("/register")
    fun addUserIntoDB(@Body user: User)
}