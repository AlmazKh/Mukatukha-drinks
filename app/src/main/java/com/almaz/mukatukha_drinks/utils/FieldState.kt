package com.almaz.mukatukha_drinks.utils

class FieldState(val data: Boolean, val message: String?) {

    companion object {
        fun success(message: String?): FieldState = FieldState(true, message)

        fun error(message: String?): FieldState = FieldState(false, message)
    }
}