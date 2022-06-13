package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.network.FoodApiService
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailVM @Inject constructor(
    private val foodApiService: FoodApiService,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    init {
        Log.e("FoodDetailVM"," init block")
    }

    fun addToCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int
    ) {
        viewModelScope.launch {
            try {
                foodApiService.addToCart(
                    foodName,
                    foodImageName,
                    foodPrice,
                    foodOrderQuantity,
                    dataStoreRepo.getUserEmail()
                )
            } catch (exception: Exception) {
                Log.e("addToCart", exception.message ?: "Empty message")
            }

        }
    }
}