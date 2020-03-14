package com.almaz.android_template_mvvm_project.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.android_template_mvvm_project.di.module.AuthModule
import com.almaz.android_template_mvvm_project.di.scope.ScreenScope
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
