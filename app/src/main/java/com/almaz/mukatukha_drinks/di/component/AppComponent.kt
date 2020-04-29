package com.almaz.mukatukha_drinks.di.component

import com.almaz.mukatukha_drinks.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            ViewModelModule::class,
            ViewModelFactoryModule::class,
            RepoModule::class,
            DatabaseModule::class
        ]
)
interface AppComponent {
    fun mainComponent(): MainComponent.Builder
    fun authComponent(): AuthComponent.Builder
    fun profileComponent(): ProfileComponent.Builder
    fun cafeComponent(): CafeComponent.Builder
}
