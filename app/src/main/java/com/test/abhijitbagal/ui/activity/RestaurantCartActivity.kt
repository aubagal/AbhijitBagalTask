package com.test.abhijitbagal.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.abhijitbagal.data.RestaurantData
import com.test.abhijitbagal.databinding.ActivityRestaurantCartBinding
import com.test.abhijitbagal.ui.adapter.RestaurantItemAdapter
import com.test.abhijitbagal.viewmodel.RestaurantViewModel
import com.test.abhijitbagal.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RestaurantCartActivity : AppCompatActivity(), RestaurantItemAdapter.ItemClick {
    private lateinit var mainViewModel: RestaurantViewModel
    private lateinit var adapter: RestaurantItemAdapter
    private lateinit var binding: ActivityRestaurantCartBinding
    var isFirst=true
    var items: List<RestaurantData> =ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setupObserver()

    }

    private fun setupUI() {
        binding.rvRestaurantItems.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantItemAdapter(this, arrayListOf(), this, true)
        binding.rvRestaurantItems.addItemDecoration(
            DividerItemDecoration(
                binding.rvRestaurantItems.context,
                (binding.rvRestaurantItems.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvRestaurantItems.adapter = adapter

        binding.txtViewAll.setOnClickListener{
            isFirst=false
            binding.txtViewAll.visibility=View.GONE
            renderList(items)
        }

    }

    private fun setupObserver() {
        GlobalScope.launch(Dispatchers.Main) { }
        mainViewModel.restaurantData.observe(this, {
            items=it
            var items=   it.filter { it.itemCount!=0 }
           if (items.size>2 && isFirst){
               binding.txtViewAll.visibility=View.VISIBLE
               var list:ArrayList<RestaurantData> = ArrayList()
               it.forEachIndexed { index, restaurantData ->
                   if (index<=1){
                       list.add(restaurantData)
                   }
               }
               renderList(list)
           }else {
               binding.txtViewAll.visibility=View.GONE
               renderList(it)
           }
            var itemCurrancy = ""
            var totalCost = 0
            it.forEach { itt ->
                itemCurrancy = itt.currency
                totalCost += (itt.itemCount * itt.itemPrice)
            }

            binding.txtCost.text = itemCurrancy + totalCost
        })
    }

    private fun renderList(items: List<RestaurantData>) {
        adapter.addData(items as ArrayList<RestaurantData>)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory()
        ).get(RestaurantViewModel::class.java)
    }

    override fun onPlusButtonClick(data: RestaurantData, position: Int) {
        var positionCheck=0
        mainViewModel.restaurantData.value?.forEachIndexed { index, restaurantData ->
            if (data==restaurantData) positionCheck=index
        }
        var itemCount = mainViewModel.restaurantData.value?.get(positionCheck)?.itemCount
        if (itemCount in 0..19) {
            mainViewModel.restaurantData.value?.get(positionCheck)?.apply {
                this.itemCount = this.itemCount + 1
                mainViewModel.restaurantData.postValue(mainViewModel.restaurantData.value as ArrayList)
            }
        }
    }

    override fun onMinusButtonClick(data: RestaurantData, position: Int) {
        GlobalScope.async {
            var positionCheck=0
            mainViewModel.restaurantData.value?.forEachIndexed { index, restaurantData ->
                if (data==restaurantData) positionCheck=index
            }

            var itemCount = mainViewModel.restaurantData.value?.get(positionCheck)?.itemCount
            if (itemCount!! > 0) {
                mainViewModel.restaurantData.value?.get(positionCheck)?.apply {
                    this.itemCount = this.itemCount - 1
                    mainViewModel.restaurantData.postValue(mainViewModel.restaurantData.value as ArrayList)
                }
            }
        }
    }
}