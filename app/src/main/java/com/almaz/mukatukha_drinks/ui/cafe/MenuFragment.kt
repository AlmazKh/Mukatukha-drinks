package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_cafe_menu.*

class MenuFragment : BaseFragment() {

    private lateinit var menuViewPagerAdapter: MenuViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .cafeComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cafe_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle("Меню")
        setArrowToolbarVisibility(true)

        menuViewPagerAdapter = MenuViewPagerAdapter(this)
        vp_drinks_menu.adapter = menuViewPagerAdapter

        TabLayoutMediator(tabs_drinks_type, vp_drinks_menu,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position) {
                    0 -> tab.text = "Кофе"
                    1 -> tab.text = "Другие напитки"
                }
            }).attach()
    }

    override fun onResume() {
        super.onResume()
        setToolbarElevation(0F)
    }

    override fun onPause() {
        super.onPause()
        setToolbarElevation(4F)
    }
}
