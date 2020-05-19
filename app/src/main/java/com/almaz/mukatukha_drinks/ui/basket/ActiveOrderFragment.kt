package com.almaz.mukatukha_drinks.ui.basket

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.core.model.Product
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
                    setUpProductsList(it.data.products)
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun setUpProductsList(products: Map<Product, Int>) {
//        val productsMap = mutableMapOf<String, List<Pair<Product, Int>>>()
        val list = mutableListOf<Product>()
        for(elt in products) {
            list.add(elt.key)
        }
//        productsMap["Productsss"] = list


        var map: MutableMap<String?, String?>
        // коллекция для групп
        val groupDataList: ArrayList<Map<String?, String?>> =
            ArrayList()
        // заполняем коллекцию групп из массива с названиями групп
        groupDataList.add(mapOf("groupName" to "Детали заказа"))

        // список атрибутов групп для чтения
        val groupFrom = arrayOf("groupName")
        // список ID view-элементов, в которые будет помещены атрибуты групп
        val groupTo = intArrayOf(R.id.listTitle)
        // создаем общую коллекцию для коллекций элементов
        val сhildDataList: ArrayList<ArrayList<Map<String?, String?>?>> =
            ArrayList()
        // создаем коллекцию элементов для первой группы
        val сhildDataItemList: ArrayList<Map<String?, String?>?> =
            ArrayList()
        // заполняем список атрибутов для каждого элемента
        // заполняем список атрибутов для каждого элемента
        for (elt in list) {
            map = HashMap()
            map["productName"] = elt.name
            сhildDataItemList.add(map)
        }
        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList)
        // список атрибутов элементов для чтения
        val childFrom = arrayOf("productName")
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        val childTo = intArrayOf(R.id.expandedListItem)

        val adapter = SimpleExpandableListAdapter(
            rootActivity, groupDataList,
            R.layout.exp_group, groupFrom,
            groupTo, сhildDataList, R.layout.exp_item,
            childFrom, childTo
        )

        expandable_list_of_products.setAdapter(adapter)
    }

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
