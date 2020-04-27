package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.ProductCategory
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_cafe_menu.*
import javax.inject.Inject


class MenuFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MenuViewModel
    private lateinit var menuViewPagerAdapter: MenuViewPagerAdapter

    private var listenersSettingUp = false

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
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.GONE
        )
        setToolbarTitle("Меню")
        setArrowToolbarVisibility(true)

        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(MenuViewModel::class.java)

        viewModel.updateProductList(ProductCategory.COFFEE, false)

        menuViewPagerAdapter = MenuViewPagerAdapter(this)
        vp_drinks_menu.adapter = menuViewPagerAdapter

        setUpListeners()

        observeShowLoadingLiveData()
    }

    override fun onResume() {
        super.onResume()
        setToolbarElevation(0F)
    }

    override fun onPause() {
        super.onPause()
        setToolbarElevation(4F)
    }

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    private fun setUpListeners() {
        TabLayoutMediator(tabs_drinks_type, vp_drinks_menu,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = ProductCategory.COFFEE.getStringValue()
                    1 -> tab.text = ProductCategory.OTHER_DRINKS.getStringValue()
                }
            }).attach()

        switch_with_milk.setOnCheckedChangeListener { _, isChecked ->
            when (tabs_drinks_type.selectedTabPosition) {
                0 -> viewModel.updateProductList(ProductCategory.COFFEE, isChecked)
                1 -> viewModel.updateProductList(ProductCategory.OTHER_DRINKS, isChecked)
            }
        }

        tabs_drinks_type.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.updateProductList(
                        ProductCategory.COFFEE,
                        switch_with_milk.isSelected
                    )
                    1 -> viewModel.updateProductList(
                        ProductCategory.OTHER_DRINKS,
                        switch_with_milk.isSelected
                    )
                }
            }
        })
    }
}
