package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.ProfileModule
import com.almaz.mukatukha_drinks.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfileModule::class
    ]
)
interface ProfileComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)
}