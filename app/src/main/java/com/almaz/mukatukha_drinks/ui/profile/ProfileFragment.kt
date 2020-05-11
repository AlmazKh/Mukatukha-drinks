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
import com.almaz.mukatukha_drinks.utils.AuthenticationState
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
        viewModel = ViewModelProvider(rootActivity, this.viewModeFactory)
            .get(ProfileViewModel::class.java)

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setToolbarTitle(getString(R.string.profile_page_title))

        setUpMenuListeners()
        btn_logout.setOnClickListener {
            viewModel.logout()
        }
        text_input_layout_share.setOnClickListener {}



        observeAuthenticationState()
        observeUserLiveData()
    }

    private fun setUpMenuListeners() {
        tv_my_data.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_myDataFragment)
        }
        tv_lovely_drinks.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_lovelyDrinksFragment)
        }
        tv_daily_facts.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_dailyFactsFragment)
        }
        tv_history.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_historyFragment)
        }
        tv_notification.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_notificationFragment)
        }
        tv_payment_method.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_paymentFragment)
        }
        tv_support.setOnClickListener {
            rootActivity.navController.navigate(R.id.action_profileFragment_to_supportFragment)
        }
    }

    private fun observeAuthenticationState() =
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authState ->
            if (authState != null) {
                when (authState) {
                    AuthenticationState.UNAUTHENTICATED -> {
                        rootActivity.navController.navigate(R.id.loginFragment)
                    }
                    AuthenticationState.AUTHENTICATED -> {
                        viewModel.updateUserInfo()
                    }
                    AuthenticationState.INVALID_AUTHENTICATION -> { }
                }
            }
        })

    private fun observeUserLiveData() =
        viewModel.userDataLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    tv_user_name.text = it.data.name
                    tv_points_title.text = it.data.discountPoints.toString()
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}
