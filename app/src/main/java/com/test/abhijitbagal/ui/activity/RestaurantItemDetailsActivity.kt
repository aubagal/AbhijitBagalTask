package com.test.abhijitbagal.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.abhijitbagal.data.RestaurantData
import com.test.abhijitbagal.databinding.ActivityRestaurantItemDetailsBinding
import com.test.abhijitbagal.ui.adapter.RestaurantItemAdapter
import com.test.abhijitbagal.viewmodel.RestaurantViewModel
import com.test.abhijitbagal.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RestaurantItemDetailsActivity : AppCompatActivity(), RestaurantItemAdapter.ItemClick {
    private lateinit var mainViewModel: RestaurantViewModel
    private lateinit var adapter: RestaurantItemAdapter
    private lateinit var binding: ActivityRestaurantItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        binding.rvRestaurantItems.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantItemAdapter(this, arrayListOf(), this, false)
        binding.rvRestaurantItems.addItemDecoration(
            DividerItemDecoration(
                binding.rvRestaurantItems.context,
                (binding.rvRestaurantItems.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvRestaurantItems.adapter = adapter

        binding.layoutCart.setOnClickListener {
            startActivity(Intent(this, RestaurantCartActivity::class.java))
        }
    }

    private fun setupObserver() {
        GlobalScope.launch(Dispatchers.Main) { }
        mainViewModel.restaurantData.observe(this, {
            renderList(it)
            var itemCount = 0
            it.forEach { itt ->
                itemCount += itt.itemCount
            }
            binding.txtItemCount.text = "VIEW CART (${itemCount} ITEMS)"
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
        ).get(RestaurantViewModel::class.java).apply { putRestaurantData() }
    }

    override fun onPlusButtonClick(data: RestaurantData, position: Int) {
        var itemCount = mainViewModel.restaurantData.value?.get(position)?.itemCount
        if (itemCount in 0..19) {
            mainViewModel.restaurantData.value?.get(position)?.apply {
                this.itemCount = this.itemCount + 1
                mainViewModel.restaurantData.postValue(mainViewModel.restaurantData.value as ArrayList)
            }
        }
    }

    override fun onMinusButtonClick(data: RestaurantData, position: Int) {
        var itemCount = mainViewModel.restaurantData.value?.get(position)?.itemCount
        if (itemCount!! > 0) {
            mainViewModel.restaurantData.value?.get(position)?.apply {
                this.itemCount = this.itemCount - 1
                mainViewModel.restaurantData.postValue(mainViewModel.restaurantData.value as ArrayList)
            }
        }
    }
}