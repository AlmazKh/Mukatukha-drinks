package com.almaz.mukatukha_drinks.ui.special_offer

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cafe_list.*
import kotlinx.android.synthetic.main.fragment_special_offer.*
import javax.inject.Inject

class SpecialOfferFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SpecialOfferViewModel
    private lateinit var offersAdapter: OffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .cafeComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_special_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_cafes.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(SpecialOfferViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setToolbarTitle("Акции")
        initAdapter()

        observeShowLoadingLiveData()
        observeCafeListLiveData()
        observeCafeClickLiveData()
    }

    private fun initAdapter() {

        rv_discounts.adapter =  OffersAdapter()
        rv_promocodes.adapter =  PromocodeAdapter()
        rv_happy_hours.adapter = HappyHourAdapter()
        viewModel.updateOffersList()
    }

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    private fun observeCafeListLiveData() =
        viewModel.offersLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    cafeAdapter.submitList(it.data)
                    rv_cafes.adapter = cafeAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
}
