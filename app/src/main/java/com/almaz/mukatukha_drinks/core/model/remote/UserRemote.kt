package com.almaz.mukatukha_drinks.core.model.remote

//TODO need to fix
data class UserRemote(
    val id: Long,
    val name: String?,
    val phoneNumber: String?,
    val email: String?,
    val discountPoints: Int = 0
)