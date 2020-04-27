package com.almaz.mukatukha_drinks.ui.cafe

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
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject

class ProductListFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MenuViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .cafeComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_product_list, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_menu.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(MenuViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.GONE
        )
        setArrowToolbarVisibility(true)

        initAdapter()

        observeProductListLiveData()
        observeProductClickLiveData()
    }

    private fun initAdapter() {
        productAdapter = ProductAdapter {
            viewModel.onProductClick(it)
        }
        rv_menu.adapter = productAdapter
    }

    private fun observeProductListLiveData() =
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    productAdapter.submitList(it.data)
                    rv_menu.adapter = productAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeProductClickLiveData() =
        viewModel.productClickLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    /*rootActivity.navController.navigate(
                        R.id.action_productListFragment_to_productDialogFragment,
                        bundleOf("product" to it.data)
                    )*/
                    ProductDialogFragment.newInstance(it.data).show(childFragmentManager, "dialog")
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}
