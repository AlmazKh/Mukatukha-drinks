package com.almaz.mukatukha_drinks.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.almaz.mukatukha_drinks.core.interactors.LoginInteractor
import com.almaz.mukatukha_drinks.ui.base.BaseViewModel
import com.almaz.mukatukha_drinks.utils.FieldState
import com.almaz.mukatukha_drinks.utils.LoginState
import com.almaz.mukatukha_drinks.utils.ScreenState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class LoginViewModel
@Inject constructor(
    private val loginInteractor: LoginInteractor
) : BaseViewModel() {

    val loginState = MutableLiveData<ScreenState<LoginState>>()
    val phoneLoginProcessState = MutableLiveData<FieldState>()
    private var storedVerificationId: String? = null
    private var storedPhoneNumber: String? = null

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        loginState.value = ScreenState.Loading
        disposables.add(
            loginInteractor.loginWithGoogle(acct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginState.value = ScreenState.Render(LoginState.SUCCESS_LOGIN)
                }, {
                    loginState.value = ScreenState.Render(LoginState.ERROR)
                    it.printStackTrace()
                })
        )
    }

    fun onGoogleIntentResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // login_btn_background Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    fun sendVerificationCode(phoneNumber: String) {
        disposables.addAll(
            loginInteractor.sendVerificationCode(phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    FieldState.success("Мы отправили вам код верификации")
                    storedVerificationId = it
                    storedPhoneNumber = phoneNumber
                }, {
                    loginState.value = ScreenState.Render(LoginState.ERROR)
                    it.printStackTrace()
                }, {
                    Log.d("MYLOG", "success sign in")
                    loginState.value = ScreenState.Render(LoginState.SUCCESS_LOGIN)
                })
        )
    }

    fun verifySignInCode(verificationCode: String, userName: String, phoneNumber: String) {
        if (storedVerificationId == null) {
            phoneLoginProcessState.value = FieldState.error("Сначала введите номер телефона")
            return
        }
        if (storedPhoneNumber == null) {
            phoneLoginProcessState.value = FieldState.error("Введите корректный номер телефона")
            return
        }
        if (storedPhoneNumber != phoneNumber) {
            phoneLoginProcessState.value =
                FieldState.error("Фактический и введенные номера не совпадают")
        }
        loginState.value = ScreenState.Loading
        disposables.add(
            loginInteractor.loginWithPhone(
                storedVerificationId!!,
                verificationCode,
                userName,
                phoneNumber
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginState.value = ScreenState.Render(LoginState.SUCCESS_LOGIN)
                }, {
                    loginState.value = ScreenState.Render(LoginState.ERROR)
                    it.printStackTrace()
                })
        )
    }

    companion object {
        internal const val RC_SIGN_IN = 228
    }
}

