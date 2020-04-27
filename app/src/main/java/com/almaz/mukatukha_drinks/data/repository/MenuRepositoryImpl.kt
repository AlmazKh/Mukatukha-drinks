package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl
@Inject constructor() : MenuRepository {

    override fun getProductList(productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>> {
        return Single.just(listOf(
            Product(
                "1",
                "Американо",
                "100 руб.",
                "0.3 л",
                ProductCategory.COFFEE,
                false,
                "Some text with description"
            ),
            Product(
                "2",
                "Капучино",
                "150 руб.",
                "0.5 л",
                ProductCategory.COFFEE,
                true,
                "Some text with other description in two lines may be"
            )
        ))
    }
}