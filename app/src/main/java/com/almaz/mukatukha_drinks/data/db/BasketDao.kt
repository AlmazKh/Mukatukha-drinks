package com.almaz.mukatukha_drinks.data.db

import androidx.room.*
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import com.almaz.mukatukha_drinks.core.model.db.BasketDB
import com.almaz.mukatukha_drinks.core.model.db.ProductDB
import io.reactivex.Single

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket")
    fun getItemsFromBasket(): Single<List<BasketAndProduct>>

    @Transaction
    @Query("SELECT * FROM basket WHERE product_id = :productId")
    fun getProductById(productId: Long): Single<BasketAndProduct>

    @Transaction
    @Query("SELECT amount FROM basket WHERE product_id = :productId")
    fun getProductAmountById(productId: Long): Int?

    @Insert
    fun insertItemIntoBasket(item: BasketDB)

    @Query("UPDATE basket SET amount = :amount WHERE product_id = :productId AND owner_id = :ownerId")
    fun updateProductAmount(productId: Long, amount: Int, ownerId: Long?)

    @Update
    fun update(item: BasketDB)

    @Delete
    fun delete(item: BasketDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductDB)
}