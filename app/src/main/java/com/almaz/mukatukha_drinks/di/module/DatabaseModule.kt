package com.almaz.mukatukha_drinks.di.module

import android.content.Context
import androidx.room.Room
import com.almaz.mukatukha_drinks.data.db.AppDatabase
import com.almaz.mukatukha_drinks.data.db.BasketDao
import com.almaz.mukatukha_drinks.data.db.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBasketDao(appDatabase: AppDatabase): BasketDao = appDatabase.basketDao()
}
