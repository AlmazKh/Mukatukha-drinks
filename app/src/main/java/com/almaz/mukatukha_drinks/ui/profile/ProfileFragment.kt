package com.almaz.mukatukha_drinks.ui.profile

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
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .profileComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        init()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setToolbarTitle(getString(R.string.profile_page_title))

        tv_my_data.setOnClickListener {}
        tv_lovely_drinks.setOnClickListener {}
        tv_history.setOnClickListener {}
        tv_payment_method.setOnClickListener {}
        tv_support.setOnClickListener {}

        btn_logout.setOnClickListener {
            viewModel.logout()
        }
        text_input_layout_share.setOnClickListener {}
    }

    private fun init() {
        viewModel = ViewModelProvider(this, this.viewModeFactory)
            .get(ProfileViewModel::class.java)

        viewModel.checkAuthUser()
        observeIsLoginedLiveData()
    }

    private fun observeIsLoginedLiveData() =
        viewModel.isLoginedLiveData.observe(viewLifecycleOwner, Observer { response ->
            if (response.data != null) {
                if (response.data) {
                    showSnackbar("Welcome to your Profile")
                    //TODO: set up data like name and points
//                    rootActivity.navController.navigate(R.id.action_loginFragment_to_profileFragment)
                } else {
                    rootActivity.navController.navigate(R.id.action_profileFragment_to_loginFragment)
                }
            }
        })
}
