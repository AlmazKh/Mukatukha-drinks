package com.almaz.mukatukha_drinks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.almaz.mukatukha_drinks.core.model.db.BasketDB

const val DB_NAME: String = "MUKATUKHA.db"

@Database(entities = [BasketDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}