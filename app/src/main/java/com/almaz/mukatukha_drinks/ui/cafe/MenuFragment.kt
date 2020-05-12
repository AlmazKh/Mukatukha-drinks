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
import com.almaz.mukatukha_drinks.core.model.Cafe
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

        viewModel.updateProductList(
            arguments?.getParcelable<Cafe>("cafe")?.id!!,
            when (tabs_drinks_type.selectedTabPosition) {
                1 -> ProductCategory.OTHERS
                else -> ProductCategory.COFFEE
            },
            switch_with_milk.isChecked
        )

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

    override fun onDestroy() {
        super.onDestroy()
        setArrowToolbarVisibility(false)
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
                    1 -> tab.text = ProductCategory.OTHERS.getStringValue()
                }
            }).attach()

        switch_with_milk.setOnCheckedChangeListener { _, isChecked ->
            when (tabs_drinks_type.selectedTabPosition) {
                0 -> viewModel.updateProductList(
                    arguments?.getParcelable<Cafe>("cafe")?.id!!,
                    ProductCategory.COFFEE,
                    isChecked
                )
                1 -> viewModel.updateProductList(
                    arguments?.getParcelable<Cafe>("cafe")?.id!!,
                    ProductCategory.OTHERS,
                    isChecked
                )
            }
        }

        tabs_drinks_type.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.updateProductList(
                        arguments?.getParcelable<Cafe>("cafe")?.id!!,
                        ProductCategory.COFFEE,
                        switch_with_milk.isChecked
                    )
                    1 -> viewModel.updateProductList(
                        arguments?.getParcelable<Cafe>("cafe")?.id!!,
                        ProductCategory.OTHERS,
                        switch_with_milk.isChecked
                    )
                }
            }
        })

        btn_go_to_basket.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_cafeMenuFragment_to_basketFragment)
        }
    }
}
