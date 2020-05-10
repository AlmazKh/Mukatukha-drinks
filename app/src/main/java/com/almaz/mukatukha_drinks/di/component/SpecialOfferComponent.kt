package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.special_offer.SpecialOfferFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
@ScreenScope
interface SpecialOfferComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): SpecialOfferComponent
    }

    fun inject(specialOfferFragment: SpecialOfferFragment)
}