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

const val PHONE_NUMBER_LENGHT = 10

class LoginViewModel
@Inject constructor(
    private val loginInteractor: LoginInteractor
) : BaseViewModel() {

    val loginState = MutableLiveData<ScreenState<LoginState>>()
    val phoneLoginProcessState = MutableLiveData<FieldState<Boolean>>()
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
        if (phoneNumber.isEmpty()) {
            phoneLoginProcessState.value = FieldState(null, "Введите номер телефона")
            return
        }
        if (phoneNumber.length < PHONE_NUMBER_LENGHT) {
            phoneLoginProcessState.value = FieldState(null, "Введите корректный номер телефона")
            return
        }
        disposables.addAll(
            loginInteractor.sendVerificationCode(phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    phoneLoginProcessState.value =
                        FieldState(null, "Мы отправили вам код верификации")
                    storedVerificationId = it
                    storedPhoneNumber = phoneNumber
                }, {
                    phoneLoginProcessState.value = FieldState(null, "Возникла ошибка")
                }, {
                    Log.d("MYLOG", "success send verif")
                    phoneLoginProcessState.value = FieldState(null, "Успешно!")
                })
        )
    }

    fun verifySignInCode(verificationCode: String, userName: String, phoneNumber: String) {
        if (storedVerificationId == null) {
            phoneLoginProcessState.value = FieldState(null, "Сначала введите номер телефона")
            return
        }
        if (storedPhoneNumber == null) {
            phoneLoginProcessState.value = FieldState(null, "Введите корректный номер телефона")
            return
        }
        if (storedPhoneNumber != phoneNumber) {
            phoneLoginProcessState.value = FieldState(null, "Введите корректный номер телефона")
        }
        disposables.add(
            loginInteractor.loginWithPhone(
                storedVerificationId!!,
                verificationCode,
                userName,
                phoneNumber
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("MYLOG", "success verif")
                    phoneLoginProcessState.value =
                        FieldState(true, "Введите корректный номер телефона")
                }, {
                    phoneLoginProcessState.value = FieldState(null, "Ошибка. Попробуйте еще раз")
                    it.printStackTrace()
                })
        )
    }

    companion object {
        internal const val RC_SIGN_IN = 228
    }
}

