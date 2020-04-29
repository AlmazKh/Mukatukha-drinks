package com.almaz.mukatukha_drinks.core.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.almaz.mukatukha_drinks.core.model.User

@Entity(tableName = "basket")
data class BasketDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "product_id")
    val productId: Long,
    val amount: Int = 0,
    @ColumnInfo(name = "owner_id")
    val ownerId: Long
)