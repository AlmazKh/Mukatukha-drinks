package com.almaz.mukatukha_drinks.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.ResHolder
import com.almaz.mukatukha_drinks.di.scope.ScreenScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides

@Module
class AuthModule {
    @ScreenScope
    @Provides
    fun provideSignInOptions(resHolder: ResHolder): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resHolder.resources.getString(R.string.web_client_id))
            .requestEmail()
            .build()
    }

    @ScreenScope
    @Provides
    fun provideGoogleSignInClient(
        activity: AppCompatActivity,
        options: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, options)
    }

    @ScreenScope
    @Provides
    fun provideResources(context: Context): ResHolder {
        return ResHolder(context.resources)
    }
}