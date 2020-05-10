package com.almaz.mukatukha_drinks.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.mukatukha_drinks.di.module.ProfileModule
import com.almaz.mukatukha_drinks.ui.profile.ProfileFragment
import com.almaz.mukatukha_drinks.ui.profile.daily_facts.DailyFactsFragment
import com.almaz.mukatukha_drinks.ui.profile.history.HistoryFragment
import com.almaz.mukatukha_drinks.ui.profile.lovely_drinks.LovelyDrinksFragment
import com.almaz.mukatukha_drinks.ui.profile.my_data.MyDataFragment
import com.almaz.mukatukha_drinks.ui.profile.notification.NotificationFragment
import com.almaz.mukatukha_drinks.ui.profile.payment_method.PaymentFragment
import com.almaz.mukatukha_drinks.ui.profile.support.SupportFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfileModule::class
    ]
)
interface ProfileComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)
    fun inject(dailyFactsFragment: DailyFactsFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(lovelyDrinksFragment: LovelyDrinksFragment)
    fun inject(myDataFragment: MyDataFragment)
    fun inject(notificationFragment: NotificationFragment)
    fun inject(paymentFragment: PaymentFragment)
    fun inject(supportFragment: SupportFragment)
}
