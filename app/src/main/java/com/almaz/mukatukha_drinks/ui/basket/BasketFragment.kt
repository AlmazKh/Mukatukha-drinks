package com.almaz.mukatukha_drinks.ui.basket

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_ADD
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_PRODUCT
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_REMOVE
import com.almaz.mukatukha_drinks.ui.cafe.ProductDialogFragment
import kotlinx.android.synthetic.main.fragment_basket.*
import javax.inject.Inject

class BasketFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BasketViewModel
    private lateinit var basketProductAdapter: BasketProductAdapter

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
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_basket_product.apply {
            layoutManager = LinearLayoutManager(rootView.context)
            addItemDecoration(DividerItemDecoration(rootView.context, HORIZONTAL))
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(BasketViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setArrowToolbarVisibility(false)
        setToolbarTitle("Ваши заказы")
        // TODO
        //viewModel.checkHasActiveOrder()

        initAdapter()
        addPaymentMethod()

        btn_make_order.setOnClickListener {
            if (checkFields()) {
                viewModel.makeOrder(
                    et_phone_number.text.toString()
                )
            }
        }

        observeShowLoadingLiveData()
        observeProductListLiveData()
        observeProductClickLiveData()
        observeOrderStatusLiveData()
    }

    private fun initAdapter() {
        basketProductAdapter = BasketProductAdapter {
            when (it.first) {
                EVENT_KEY_PRODUCT -> viewModel.onProductClick(it.second)
                EVENT_KEY_ADD -> viewModel.onAddProductClick(it.second)
                EVENT_KEY_REMOVE -> viewModel.onRemoveProductClick(it.second)
            }
        }
        rv_basket_product.adapter = basketProductAdapter
        viewModel.updateBasketProductList()
    }

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    private fun observeProductListLiveData() =
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    basketProductAdapter.submitList(it.data)
                    rv_basket_product.adapter = basketProductAdapter
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
                    ProductDialogFragment.newInstance(it.data).show(childFragmentManager, "dialog")
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeOrderStatusLiveData() =
        viewModel.orderStatusLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    rootActivity.navController.navigate(
                        R.id.action_basketFragment_to_activeOrderFragment,
                        bundleOf("order" to it.data)
                    )
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun addPaymentMethod() {
        val adapter = ArrayAdapter(
            rootActivity, android.R.layout.simple_spinner_item, listOf(
                "Карта", "Наличные", "Google Pay"
            )
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_payment_method.adapter = adapter
    }

    private fun checkFields(): Boolean =
        et_phone_number.text.toString().trim() != ""
}
