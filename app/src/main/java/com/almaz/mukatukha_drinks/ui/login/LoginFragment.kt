package com.almaz.mukatukha_drinks.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.almaz.mukatukha_drinks.ui.login.LoginViewModel.Companion.RC_SIGN_IN
import com.almaz.mukatukha_drinks.ui.profile.ProfileViewModel
import com.almaz.mukatukha_drinks.utils.AuthenticationState
import com.almaz.mukatukha_drinks.utils.LoginState
import com.almaz.mukatukha_drinks.utils.ScreenState
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {
    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var profileViewModel: ProfileViewModel
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .authComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        init(rootView)
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onGoogleIntentResult(requestCode, data)
    }

    private fun init(view: View) {
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setToolbarTitle(getString(R.string.login_page_title))

        viewModel = ViewModelProvider(this, this.viewModeFactory)
            .get(LoginViewModel::class.java)
        profileViewModel = ViewModelProvider(rootActivity, this.viewModeFactory)
            .get(ProfileViewModel::class.java)

        view.btn_login_by_google.setOnClickListener { onGoogleSignInClick() }
        view.btn_login_by_phone.setOnClickListener { onPhoneSignInClick() }

        observeLoginState()
    }

    private fun onGoogleSignInClick() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun onPhoneSignInClick() {
        if (et_phone_number.text.toString().isEmpty()) {
            showSnackbar("Введите номер телефона")
            return
        }
        if (et_phone_number.text.toString().length < PHONE_NUMBER_LENGHT) {
            showSnackbar("Введите корректный номер телефона")
            return
        }
        rootActivity.navController.navigate(
            R.id.action_loginFragment_to_loginWithPhoneFragment,
            bundleOf("phone_number" to et_phone_number.text.toString())
        )
    }

    private fun observeLoginState() {
        viewModel.loginState.observe(viewLifecycleOwner, Observer {
            it?.let { screenState -> updateUI(screenState) }
        })
    }

    private fun updateUI(screenState: ScreenState<LoginState>?) {
        when (screenState) {
            ScreenState.Loading -> rootActivity.showLoading(true)
            is ScreenState.Render -> processLoginState(screenState.renderState)
        }
    }

    private fun processLoginState(renderState: LoginState) {
        rootActivity.showLoading(false)
        when (renderState) {
            LoginState.SUCCESS_LOGIN -> {
                profileViewModel.authenticationState.value = AuthenticationState.AUTHENTICATED
                showSnackbar("Welcome back to Mukatukha Drinks!")
                rootActivity.navController.popBackStack()
            }
            LoginState.SUCCESS_REGISTER -> {
                profileViewModel.authenticationState.value = AuthenticationState.AUTHENTICATED
                showSnackbar("Welcome to Mukatukha Drinks!")
                rootActivity.navController.popBackStack()
            }
            LoginState.ERROR -> view?.let {
                profileViewModel.authenticationState.value =
                    AuthenticationState.INVALID_AUTHENTICATION
                showSnackbar(getString(R.string.snackbar_error_message))
            }
        }
    }

    companion object {
        const val PHONE_NUMBER_LENGHT = 10
    }
}
