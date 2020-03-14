package com.almaz.android_template_mvvm_project.core.interactors

import com.almaz.android_template_mvvm_project.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor
@Inject constructor(
        private val userRepository: UserRepository
)
