package com.almaz.android_template_mvvm_project

import android.app.Application
import com.almaz.android_template_mvvm_project.di.component.AppComponent
import com.almaz.android_template_mvvm_project.di.component.DaggerAppComponent
import com.almaz.android_template_mvvm_project.di.module.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
