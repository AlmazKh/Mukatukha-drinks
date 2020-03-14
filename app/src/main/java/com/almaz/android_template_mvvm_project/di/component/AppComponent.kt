package com.almaz.android_template_mvvm_project.di.component

import com.almaz.android_template_mvvm_project.di.module.AppModule
import com.almaz.android_template_mvvm_project.di.module.RepoModule
import com.almaz.android_template_mvvm_project.di.module.ViewModelFactoryModule
import com.almaz.android_template_mvvm_project.di.module.ViewModelModule
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
}
