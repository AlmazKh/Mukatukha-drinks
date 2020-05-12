package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.AuthModule
import com.almaz.mukatukha_drinks.di.module.BasketModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.basket.ActiveOrderFragment
import com.almaz.mukatukha_drinks.ui.basket.BasketFragment
import com.almaz.mukatukha_drinks.ui.basket.EmptyBasketFragment
import com.almaz.mukatukha_drinks.ui.login.LoginFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        BasketModule::class
    ]
)
@ScreenScope
interface BasketComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): BasketComponent
    }

    fun inject(basketFragment: BasketFragment)
    fun inject(activeOrderFragment: ActiveOrderFragment)
    fun inject(emptyBasketFragment: EmptyBasketFragment)
}
