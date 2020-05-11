package com.almaz.mukatukha_drinks.di.module

import com.almaz.mukatukha_drinks.core.interfaces.*
import com.almaz.mukatukha_drinks.data.repository.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindCafeRepository(cafeRepositoryImpl: CafeRepositoryImpl): CafeRepository

    @Binds
    @Singleton
    fun bindMenuRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository

    @Binds
    @Singleton
    fun bindBasketRepository(basketRepositoryImpl: BasketRepositoryImpl): BasketRepository

    @Binds
    @Singleton
    fun bindSpecialOfferRepository(specialOfferRepositoryImpl: SpecialOfferRepositoryImpl): SpecialOfferRepository

    @Binds
    @Singleton
    fun bindProfileMenuRepository(profileMenuRepositoryImpl: ProfileMenuRepositoryImpl): ProfileMenuRepository
}
