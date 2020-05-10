package com.almaz.mukatukha_drinks.ui.cafe

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cafe_list.*
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
            bottomNavVisibility = View.VISIBLE
        )
        setToolbarTitle("Кофейни")
        initAdapter()

        observeShowLoadingLiveData()
        observeCafeListLiveData()
        observeCafeClickLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toollbar_with_search, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
//        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cafeAdapter.filter.filter(newText)
                return false
            }

        })
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
