package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.MainModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
        modules = [
            MainModule::class
        ]
)
@ScreenScope
interface MainComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}