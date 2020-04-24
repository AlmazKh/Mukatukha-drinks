package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cafe_list.*
import kotlinx.android.synthetic.main.item_fragment_cafe_list.view.*
import javax.inject.Inject

class CafeListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CafeListViewModel
    private lateinit var cafeAdapter: CafeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .cafeComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cafe_list, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_cafes.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(CafeListViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.GONE
        )
        setArrowToolbarVisibility(true)

        initAdapter()

        observeShowLoadingLiveData()
        observeCafeListLiveData()
        observeCafeClickLiveData()
    }

    private fun initAdapter() {
        cafeAdapter = CafeAdapter {
            viewModel.onCafeClick(it)
        }
        rv_cafes.adapter = cafeAdapter
        viewModel.updateCafeList()
    }

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    private fun observeCafeListLiveData() =
        viewModel.cafeListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    cafeAdapter.submitList(it.data)
                    rv_cafes.adapter = cafeAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeCafeClickLiveData() =
        viewModel.cabinetClickLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    rootActivity.navController.navigate(
                        R.id.action_cafeListFragment_to_cafeMenuFragment,
                        bundleOf("cafe" to it.data)
                    )
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}
