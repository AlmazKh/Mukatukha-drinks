package com.almaz.mukatukha_drinks.data.repository

import com.almaz.mukatukha_drinks.core.interfaces.MenuRepository
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import io.reactivex.Single
import javax.inject.Inject

class MenuRepositoryImpl
@Inject constructor() : MenuRepository {

    override fun getProductList(productCategory: ProductCategory, withMilk: Boolean): Single<List<Product>> {
        return when(productCategory){
            ProductCategory.COFFEE -> {
                if (withMilk) {
                    Single.just(listOf(
                        Product(
                            "1",
                            "Латте",
                            "100 руб.",
                            "0.3 л",
                            ProductCategory.COFFEE,
                            true,
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
                } else {
                    Single.just(listOf(
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
                            "Эспрессо",
                            "150 руб.",
                            "0.5 л",
                            ProductCategory.COFFEE,
                            false,
                            "Some text with other description in two lines may be"
                        )
                    ))
                }

            }
            ProductCategory.OTHER_DRINKS -> {
                if (withMilk) {
                    Single.just(listOf(
                        Product(
                            "1",
                            "Молочный коктейль",
                            "190 руб.",
                            "0.3 л",
                            ProductCategory.OTHER_DRINKS,
                            true,
                            "Some text with description"
                        ),
                        Product(
                            "2",
                            "Чай с молоком",
                            "150 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            true,
                            "Some text with other description in two lines may be"
                        )
                    ))
                } else {
                    Single.just(listOf(
                        Product(
                            "1",
                            "Апельсиновый сок",
                            "500 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            false,
                            "Some text with description"
                        ),
                        Product(
                            "2",
                            "Морковный фреш",
                            "250 руб.",
                            "0.5 л",
                            ProductCategory.OTHER_DRINKS,
                            false,
                            "Some text with other description in two lines may be"
                        )
                    ))
                }
            }
        }
    }
}