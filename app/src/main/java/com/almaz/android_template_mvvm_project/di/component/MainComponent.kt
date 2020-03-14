package com.almaz.android_template_mvvm_project.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.android_template_mvvm_project.di.module.MainModule
import com.almaz.android_template_mvvm_project.di.scope.ScreenScope
import com.almaz.android_template_mvvm_project.ui.main.MainActivity
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