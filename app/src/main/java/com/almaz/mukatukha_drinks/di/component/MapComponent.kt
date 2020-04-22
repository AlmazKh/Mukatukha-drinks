package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.MapModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.cafe.MapFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        MapModule::class
    ]
)
@ScreenScope
interface MapComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): MapComponent
    }

    fun inject(mapFragment: MapFragment)
}