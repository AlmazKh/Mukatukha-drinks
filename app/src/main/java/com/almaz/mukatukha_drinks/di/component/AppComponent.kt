package com.almaz.mukatukha_drinks.di.component

import com.almaz.mukatukha_drinks.di.module.AppModule
import com.almaz.mukatukha_drinks.di.module.RepoModule
import com.almaz.mukatukha_drinks.di.module.ViewModelFactoryModule
import com.almaz.mukatukha_drinks.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            ViewModelModule::class,
            ViewModelFactoryModule::class,
            RepoModule::class
        ]
)
interface AppComponent {
    fun mainComponent(): MainComponent.Builder
    fun authComponent(): AuthComponent.Builder
    fun profileComponent(): ProfileComponent.Builder
    fun mapComponent(): MapComponent.Builder
}
