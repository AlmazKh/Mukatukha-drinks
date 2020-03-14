package com.almaz.android_template_mvvm_project.di.module

import com.almaz.android_template_mvvm_project.core.interfaces.UserRepository
import com.almaz.android_template_mvvm_project.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}

