package com.almaz.mukatukha_drinks.core.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class BasketAndProduct(
    @Embedded
    val basket: BasketDB,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: ProductDB
)