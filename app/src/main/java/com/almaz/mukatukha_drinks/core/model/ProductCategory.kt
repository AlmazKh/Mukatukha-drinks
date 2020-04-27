package com.almaz.mukatukha_drinks.core.model

enum class ProductCategory: CategoryValue {
    COFFEE {
        override fun getStringValue(): String = "Кофе"
    },
    OTHER_DRINKS {
        override fun getStringValue(): String = "Другие напитки"
    }
}

interface CategoryValue {
    fun getStringValue(): String
}