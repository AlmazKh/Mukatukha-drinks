package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.AuthModule
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
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

/*  TODO add some inject activities or fragment methods
     /Just example
    fun inject(loginActivity: LoginActivity)
*/
}
