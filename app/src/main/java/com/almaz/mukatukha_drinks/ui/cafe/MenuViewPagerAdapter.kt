package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.almaz.mukatukha_drinks.core.model.Product

const val ARG_PRODUCT_LIST = "products"

class MenuViewPagerAdapter(fragment: Fragment, private val list: List<Product>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = ProductListFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList(ARG_PRODUCT_LIST, ArrayList(list))
        }
        return fragment
    }
}
