package com.almaz.mukatukha_drinks.utils

class FieldState<T>(val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T, message: String?): FieldState<T> = FieldState(data, message)

        fun <T> error(error: String?): FieldState<T> = FieldState(null, error)
    }
}