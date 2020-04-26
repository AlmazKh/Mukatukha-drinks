package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.CafeModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.cafe.MenuFragment
import com.almaz.mukatukha_drinks.ui.cafe.CafeListFragment
import com.almaz.mukatukha_drinks.ui.cafe.MapFragment
import com.almaz.mukatukha_drinks.ui.cafe.ProductListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CafeModule::class
    ]
)
@ScreenScope
interface CafeComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): CafeComponent
    }

    fun inject(mapFragment: MapFragment)
    fun inject(cafeListFragment: CafeListFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(productListFragment: ProductListFragment)
}