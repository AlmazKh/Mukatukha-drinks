package com.almaz.android_template_mvvm_project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.almaz.android_template_mvvm_project.App
import com.almaz.android_template_mvvm_project.R
import com.almaz.android_template_mvvm_project.ui.base.BaseActivity
import com.almaz.itis_booking.utils.ViewModelFactory
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
                    // TODO add items into menu
                    /** /Just example
                    R.id.navigation_map -> {
                    navigateTo(MapFragment.toString(), null)
                    }*/
                    else -> {
                        return@OnNavigationItemSelectedListener false
                    }
                }
                return@OnNavigationItemSelectedListener true
            }

    fun navigateTo(fragment: String, arguments: Bundle?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            // TODO add fragments
            /** /Just example
            MapFragment.toString() -> {
            transaction.replace(
            R.id.main_container,
            MapFragment.newInstance()
            )
            }*/
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
            // TODO add navigation item
            /** /Just example
            is MapFragment -> {
            navigation.selectedItemId = R.id.navigation_map
            }*/
        }
    }
}
