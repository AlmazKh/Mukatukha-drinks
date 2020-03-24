package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.AuthModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.almaz.mukatukha_drinks.ui.login.LoginFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
        modules = [
            AuthModule::class
        ]
)
@ScreenScope
interface AuthComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): AuthComponent
    }

    fun inject(loginFragment: LoginFragment)
}
