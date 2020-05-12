package com.almaz.mukatukha_drinks.core.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    val name: String,
    val price: Double,
    val volume: String,
    val otherDetails: String?
)