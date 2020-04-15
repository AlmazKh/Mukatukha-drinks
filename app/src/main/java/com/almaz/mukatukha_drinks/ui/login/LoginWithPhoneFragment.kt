package com.almaz.mukatukha_drinks.ui.login

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
import com.almaz.mukatukha_drinks.utils.LoginState
import com.almaz.mukatukha_drinks.utils.ScreenState
import kotlinx.android.synthetic.main.fragment_login_phone.*
import kotlinx.android.synthetic.main.fragment_login_phone.view.*
import javax.inject.Inject

class LoginWithPhoneFragment : BaseFragment() {
    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .authComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_phone, container, false)
        init(rootView)
        return rootView
    }

    private fun init(view: View) {
        viewModel = ViewModelProvider(this, this.viewModeFactory)
            .get(LoginViewModel::class.java)

        sendCode()
        view.btn_send_code.setOnClickListener {
            viewModel.verifySignInCode(
                et_verification_code.text.toString(),
                et_user_name.text.toString(),
                arguments.toString()
            )
        }

        observeLoginState()
        observePhoneLoginProcessState()
    }

    private fun sendCode() {
        viewModel.sendVerificationCode(arguments.toString())
    }

    private fun observePhoneLoginProcessState() {
        viewModel.phoneLoginProcessState.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.message != null) {
                    showSnackbar(it.message)
                }
            }
        })
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
                showSnackbar("Welcome back to Mukatukha Drinks!")
                rootActivity.navController.navigateUp()
            }
            LoginState.SUCCESS_REGISTER -> {
                showSnackbar("Welcome to Mukatukha Drinks!")
                rootActivity.navController.navigateUp()
            }
            LoginState.ERROR -> view?.let {
                showSnackbar(getString(R.string.snackbar_error_message))
            }
        }
    }
}