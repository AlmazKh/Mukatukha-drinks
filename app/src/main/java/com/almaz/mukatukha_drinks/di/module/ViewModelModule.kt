package com.almaz.mukatukha_drinks.di.module

import androidx.lifecycle.ViewModel
import com.almaz.mukatukha_drinks.ui.cafe.CafeListViewModel
import com.almaz.mukatukha_drinks.ui.cafe.MapViewModel
import com.almaz.mukatukha_drinks.ui.cafe.MenuViewModel
import com.almaz.mukatukha_drinks.ui.login.LoginViewModel
import com.almaz.mukatukha_drinks.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CafeListViewModel::class)
    abstract fun bindCafeListViewModel(cafeListViewModel: CafeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindMenuViewModel(menuViewModel: MenuViewModel): ViewModel
}

@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)