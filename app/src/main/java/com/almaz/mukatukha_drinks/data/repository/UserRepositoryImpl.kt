package com.almaz.mukatukha_drinks.data.repository

import android.content.ContentValues
import android.util.Log
import com.almaz.mukatukha_drinks.core.interfaces.UserRepository
import com.almaz.mukatukha_drinks.core.model.User
import com.almaz.mukatukha_drinks.core.model.remote.UserRemote
import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val phoneAuthProvider: PhoneAuthProvider,
    private val api: MukatukhaAPI
) : UserRepository {

    override fun checkAuthUser(): Single<Boolean> = Single.just(firebaseAuth.currentUser != null)

    override fun logout(): Completable {
        firebaseAuth.signOut()
        return Completable.complete()
    }

    override fun loginWithGoogle(acct: GoogleSignInAccount): Completable {
        return Completable.create { emitter ->
                    firebaseAuth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                    acct.idToken,
                                    null
                            )
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(task.exception ?: Exception(""))
                        }
                    }
        }
            /*.doOnComplete {
                searchUserInDb(null, acct.email)
                    .observeOn(Schedulers.io())
                    .subscribe({
                        if (!it) {
                            addUserToDb(acct.displayName, acct.email, null)
                        }
                    }, {})
            }*/
    }

    override fun loginWithPhone(
        storedVerificationId: String,
        verificationCode: String,
        userName: String,
        phone: String
    ): Completable {
        return Single.fromCallable { PhoneAuthProvider.getCredential(storedVerificationId, verificationCode) }
            .flatMapCompletable { credential ->
                Completable.create { emitter ->
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(task.exception ?: Exception(""))
                        }
                    }
                }
                    /*.doOnComplete {
                        searchUserInDb(phone, null)
                            .observeOn(Schedulers.io())
                            .subscribe({
                                if (!it) {
                                    addUserToDb(userName, null, phone)
                                }
                            }, {})
                    }*/
            }
    }

    override fun sendVerificationCode(phoneNumber: String): Maybe<String> {
        return Maybe.create { emitter ->
            phoneAuthProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d("MYLOG", "onCodeSent:$verificationId")

                        // Save verification ID and resending token so we can use them later
                        emitter.onSuccess(verificationId)
                        // ...
                    }

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        emitter.onComplete()
                        Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        emitter.onError(e)
                    }
                })
        }
    }

    override fun getCurrentUser(): Single<User> {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            return if (firebaseUser.email != null) {
                Single.fromObservable(api.getUserByEmail(firebaseUser.email!!)
                    .map {
                        mapRemoteUserToLocal(it)
                    })
            } else {
                Single.fromObservable(api.getUserByPhone(firebaseUser.phoneNumber!!)
                    .map {
                        mapRemoteUserToLocal(it)
                    })
            }
            } else {
            return Single.just(
                    User(
                        id = -1,
                        name = null,
                        phoneNumber = null,
                        email = null
                    )
                )
            }
    }

    override fun getUserById(userId: Long): Single<User> {
        return Single.fromObservable(
            api.getUserById(userId)
        )
            .map {
                mapRemoteUserToLocal(it)
            }
    }

    private fun searchUserInDb(phone: String?, email: String?): Single<Boolean> {
        if (phone == null) {
            return Single.fromObservable(api.getUserByEmail(email!!)
                .map {
                    when (it) {
                        null -> false
                        else -> true
                    }
                })
        } else {
            return Single.fromObservable(api.getUserByPhone(phone)
                .map {
                    when (it) {
                        null -> false
                        else -> true
                    }
                })
        }
    }

    private fun addUserToDb(name: String?, email: String?, phone: String?) {
        api.addUserIntoDB(
            User(
                id = null,
                name = name,
                phoneNumber = phone,
                email = email
            )
        )
    }

    private fun mapRemoteUserToLocal(remote: UserRemote): User =
        User(
            remote.id.toLong(),
            remote.name,
            remote.phone,
            remote.email,
            remote.discountPoints
        )
}
