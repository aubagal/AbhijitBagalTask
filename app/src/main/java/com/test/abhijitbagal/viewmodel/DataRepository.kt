package com.test.abhijitbagal.viewmodel

import androidx.lifecycle.MutableLiveData
import com.test.abhijitbagal.data.RestaurantData

object DataRepository {
    var restaurantData: MutableLiveData<List<RestaurantData>>? = null
    fun getData(): MutableLiveData<List<RestaurantData>> {
        if (restaurantData == null) {
            restaurantData = MutableLiveData<List<RestaurantData>>()
        }
        return restaurantData as MutableLiveData<List<RestaurantData>>
    }

    fun putRestaurantData() {
        var tempList: ArrayList<RestaurantData> = ArrayList()
        var data1 = RestaurantData()
        data1.itemName = "Guac de la Costa"
        data1.itemDescription =
            "Elaborado con aguacates cuidadosamente seleccionados, cultivados de manera natural en la Costa Tropical"
        data1.itemPrice = 100
        data1.itemId = 1
        data1.itemCount = 0
        data1.currency = "$"
        tempList.add(data1)

        var data2 = RestaurantData()
        data2.itemName = "Chicharron y Cerveza"
        data2.itemDescription =
            "Chicharrón en salsa is a popular breakfast and dinner dish in Mexico, made of pork rind cooked in mild spicy salsa, seasoned with coriander"
        data2.itemPrice = 90
        data2.itemId = 2
        data2.itemCount = 0
        data2.currency = "$"
        tempList.add(data2)
        var data3 = RestaurantData()

        data3.itemName = "Chilitos con ca"
        data3.itemDescription =
            "Come celebrate CINCO DE MAYO here at CHILITO'S. We have beer bucket specials, \$2 tacos all day and more drink specials"
        data3.itemPrice = 110
        data3.itemId = 3
        data3.itemCount = 0
        data3.currency = "$"
        tempList.add(data3)

        var data4 = RestaurantData()
        data4.itemName = "Chicharron y Cerveza"
        data4.itemDescription =
            "Chicharrón en salsa is a popular breakfast and dinner dish in Mexico, made of pork rind cooked in mild spicy salsa, seasoned with coriander"
        data4.itemPrice = 9
        data4.itemId = 2
        data4.itemCount = 0
        data4.currency = "$"
        tempList.add(data4)

        restaurantData?.postValue(tempList)
    }
}