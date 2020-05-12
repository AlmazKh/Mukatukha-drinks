package com.almaz.mukatukha_drinks.ui.basket

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
import com.almaz.mukatukha_drinks.utils.BasketState
import javax.inject.Inject

class EmptyBasketFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BasketViewModel

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
        return inflater.inflate(R.layout.fragment_empty_basket, container, false)
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
        setToolbarTitle("Ваши заказы")

        viewModel.checkBasketState()
        observeBasketState()
    }

    private fun observeBasketState() =
        viewModel.basketState.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    BasketState.HAS_ACTIVE_ORDER -> {
                        rootActivity.navController.navigate(R.id.activeOrderFragment)
                    }
                    BasketState.NOT_EMPTY -> {
                        rootActivity.navController.navigate(R.id.basketFragment)
                    }
                    BasketState.EMPTY -> {
                    }
                }
            }
        })

}