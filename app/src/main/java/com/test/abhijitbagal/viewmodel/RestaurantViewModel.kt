package com.test.abhijitbagal.viewmodel

import androidx.lifecycle.ViewModel

class RestaurantViewModel : ViewModel() {
    var restaurantData = DataRepository.getData()
    fun putRestaurantData() {
        DataRepository.putRestaurantData()
    }
}