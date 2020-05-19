package com.almaz.mukatukha_drinks.ui.basket

import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.main.fragment_active_order.*
import java.util.*
import javax.inject.Inject

class ActiveOrderFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BasketViewModel
    private val handler = Handler()
    private var currentDate = Calendar.getInstance()
    private val eventDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .basketComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_active_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(BasketViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setArrowToolbarVisibility(false)
        setToolbarTitle("Ваш заказ")

        observeOrderStatusLiveData()
    }

    private fun observeOrderStatusLiveData() =
        viewModel.orderStatusLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    tv_secret_code.text = it.data.secretCode
                    tv_cafe_name.text = it.data.cafe.name
                    setUpTimer(
                        it.data.time.substringBefore(':').toInt(),
                        it.data.time.substringAfter(':').toInt()
                    )
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun setUpTimer(minutes: Int, seconds: Int) {
        eventDate[Calendar.MINUTE] =
            ((currentDate.timeInMillis + (1000 * 60) * minutes) / (1000 * 60) % 60).toInt()
        eventDate[Calendar.SECOND] = ((currentDate.timeInMillis + 1000 * seconds) / 1000 % 60).toInt()
        eventDate.timeZone = TimeZone.getTimeZone("GMT")

        // Update TextView every second
        handler.post(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                updateTime()
            }
        })
    }

    fun updateTime() {
        currentDate = Calendar.getInstance()
        val diff = eventDate.timeInMillis - currentDate.timeInMillis

        val minutes = diff / (1000 * 60) % 60
        val seconds = (diff / 1000) % 60

        // Display Countdown
        tv_time.text = "${minutes}:${seconds}"
        endEvent(currentDate, eventDate)
    }

    private fun endEvent(currentdate: Calendar, eventdate: Calendar) {
        if (currentdate.time >= eventdate.time) {
            tv_time.text = "Ваш заказ готов"
            handler.removeMessages(0)
        }
    }
}
