package com.almaz.mukatukha_drinks.core.interactors

import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor
@Inject constructor(
        private val userRepository: UserRepository
)
