package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.network.FoodApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailVM @Inject constructor(private val foodApiService: FoodApiService) : ViewModel() {

    fun addToCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        email: String
    ) {
        viewModelScope.launch {
            try {
                foodApiService.addToCart(foodName, foodImageName, foodPrice, foodOrderQuantity, email)
            }catch (exception: Exception){
                Log.e("addToCart",exception.message ?: "Empty message")
            }

        }
    }
}