package com.almaz.mukatukha_drinks.ui.profile.daily_facts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.almaz.mukatukha_drinks.ui.special_offer.SpecialOfferViewModel
import kotlinx.android.synthetic.main.fragment_daily_facts.*
import javax.inject.Inject

class DailyFactsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DailyFactsViewModel
    private lateinit var factsAdapter: FactsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .profileComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_facts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_daily_facts.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(DailyFactsViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.GONE
        )
        setToolbarTitle(getString(R.string.menu_daily_facts))
        setArrowToolbarVisibility(true)
        initAdapter()

        observeShowLoadingLiveData()
        observeOffersListLiveData()
    }

    private fun initAdapter() {
        factsAdapter = FactsAdapter()
        rv_daily_facts.adapter = factsAdapter
        viewModel.updateFactsList()
    }

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    private fun observeOffersListLiveData() =
        viewModel.factsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    factsAdapter.submitList(it.data)
                    rv_daily_facts.adapter = factsAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}
