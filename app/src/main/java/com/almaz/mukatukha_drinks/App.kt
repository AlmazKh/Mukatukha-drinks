package com.almaz.mukatukha_drinks

import android.app.Application
import com.almaz.mukatukha_drinks.di.component.AppComponent
import com.almaz.mukatukha_drinks.di.component.DaggerAppComponent
import com.almaz.mukatukha_drinks.di.module.AppModule

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
