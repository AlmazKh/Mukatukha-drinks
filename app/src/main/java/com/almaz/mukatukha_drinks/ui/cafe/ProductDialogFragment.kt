package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_product_dialog.*

const val ARG_PRODUCT = "product"

class ProductDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_product_name.text = arguments?.getParcelable<Product>("product")?.name
        tv_description.text = arguments?.getParcelable<Product>("product")?.otherDetails
    }

    companion object {
        fun newInstance(product: Product): ProductDialogFragment =
            ProductDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, product)
                }
            }
    }
}
