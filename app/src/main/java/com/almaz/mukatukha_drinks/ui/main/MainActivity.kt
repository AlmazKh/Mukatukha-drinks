package com.almaz.mukatukha_drinks.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseActivity
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.ui.basket.BasketFragment
import com.almaz.mukatukha_drinks.ui.cafe.CafeFragment
import com.almaz.mukatukha_drinks.ui.notification.NotificationFragment
import com.almaz.mukatukha_drinks.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun inject() = App.appComponent
            .mainComponent()
            .withActivity(this)
            .build()
            .inject(this)

    override fun setupView() {
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewModel = ViewModelProvider(this, this.viewModeFactory)
                .get(MainViewModel::class.java)
    }

    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_cafe -> {
                        navigateTo(CafeFragment.toString(), null)
                    }
                    R.id.navigation_basket -> {
                        navigateTo(BasketFragment.toString(), null)
                    }
                    R.id.navigation_notification -> {
                        navigateTo(NotificationFragment.toString(), null)
                    }
                    R.id.navigation_profile -> {
                        navigateTo(ProfileFragment.toString(), null)
                    }
                    else -> {
                        return@OnNavigationItemSelectedListener false
                    }
                }
                return@OnNavigationItemSelectedListener true
            }

    fun navigateTo(fragment: String, arguments: Bundle?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            CafeFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        CafeFragment.newInstance()
                )
            }
            BasketFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        BasketFragment.newInstance()
                )
            }
            NotificationFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        NotificationFragment.newInstance()
                )
            }
            ProfileFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        ProfileFragment.newInstance()
                )
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            getTopFragment()?.let {
                supportFragmentManager.beginTransaction()
                        .remove(it)
                        .commitNow()
            }
            setBottomNavSelectedItem(getTopFragment())
        } else {
            super.onBackPressed()
        }
    }

    private fun getTopFragment(): Fragment? {
        val fragmentList = supportFragmentManager.fragments
        var top: Fragment? = null
        for (i in fragmentList.indices.reversed()) {
            top = fragmentList[i] as Fragment
            return top
        }
        return top
    }

    private fun setBottomNavSelectedItem(fragment: Fragment?) {
        when (fragment) {
            is CafeFragment -> {
                navigation.selectedItemId = R.id.navigation_cafe
            }
            is BasketFragment -> {
                navigation.selectedItemId = R.id.navigation_basket
            }
            is NotificationFragment -> {
                navigation.selectedItemId = R.id.navigation_notification
            }
            is ProfileFragment -> {
                navigation.selectedItemId = R.id.navigation_profile
            }
        }
    }
}
