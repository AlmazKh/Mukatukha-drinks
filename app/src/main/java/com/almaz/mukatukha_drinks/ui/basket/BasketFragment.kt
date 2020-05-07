package com.almaz.mukatukha_drinks.ui.basket

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.almaz.mukatukha_drinks.utils.GooglePayHelper
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient
import kotlinx.android.synthetic.main.fragment_basket.*
import javax.inject.Inject

const val LOAD_PAYMENT_DATA_REQUEST_CODE = 1

class BasketFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BasketViewModel
    private lateinit var basketProductAdapter: BasketProductAdapter
    private lateinit var paymentsClient: PaymentsClient

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

        initGooglePay()
        initAdapter()
        addPaymentMethod()

        btn_make_order.setOnClickListener {
            if (checkFields()) {
                viewModel.makeOrder(
                    et_phone_number.text.toString()
                )
            }
        }

        btn_make_order.visibility = View.VISIBLE
        btn_make_order_with_googlepay.visibility = View.GONE

        spinner_payment_method.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        2 -> {
                            btn_make_order.visibility = View.GONE
                            btn_make_order_with_googlepay.visibility = View.VISIBLE
                        }
                        else -> {
                            btn_make_order.visibility = View.VISIBLE
                            btn_make_order_with_googlepay.visibility = View.GONE
                        }
                    }
                }

            }

        observeShowLoadingLiveData()
        observeProductListLiveData()
        observeProductClickLiveData()
        observeOrderStatusLiveData()
    }

    private fun initGooglePay() {
        paymentsClient = GooglePayHelper.createPaymentsClient(rootActivity)
        (GooglePayHelper.isReadyToPay(paymentsClient)).addOnCompleteListener {
            if (it.result!!) {
                btn_make_order_with_googlepay.visibility = View.VISIBLE
            } else {
                btn_make_order_with_googlepay.visibility = View.GONE
            }
        }
        btn_make_order_with_googlepay.setOnClickListener {
            requestPayment()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data?.let {
                            onPaymentSuccess(PaymentData.getFromIntent(data))
                        }
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        onError(AutoResolveHelper.getStatusFromIntent(data)?.statusCode)
                    }
                }
            }
        }
    }

    private fun onPaymentSuccess(paymentData: PaymentData?) {
        Toast.makeText(rootActivity, "Payment Success", Toast.LENGTH_LONG).show()
    }

    private fun onError(statusCode: Int?) {
        Toast.makeText(rootActivity, "Payment Error", Toast.LENGTH_LONG).show()
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }

    private fun requestPayment() {
        val request = GooglePayHelper.createPaymentDataRequest()
        val futurePaymentData = paymentsClient.loadPaymentData(request)
        AutoResolveHelper.resolveTask(
            futurePaymentData,
            rootActivity,
            LOAD_PAYMENT_DATA_REQUEST_CODE
        )
    }
}
