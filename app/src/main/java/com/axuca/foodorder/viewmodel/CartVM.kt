package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.model.CartFoodItem
import com.axuca.foodorder.network.FoodApiService
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(
    private val foodApiService: FoodApiService,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val _foods = MutableLiveData<List<CartFoodItem>>()

    val foods: LiveData<List<CartFoodItem>>
        get() = _foods

    private val _totalPrice = MutableLiveData<Int>()

    val totalPrice: LiveData<Int>
        get() = _totalPrice

    init {
        getFoodsFromCart()
    }

    private fun calculateTotalPrice() {
        _totalPrice.value = _foods.value!!.sumOf {
            it.orderQuantity.toInt() * it.price.toInt()
        }
    }

    fun getFoodsFromCart() {
        viewModelScope.launch {
            try {
                Log.e("getFromCart", "trying to get")

                val userEmail = dataStoreRepo.getUserEmail()
                Log.e("Email", userEmail)

                val data = foodApiService.getFromCart(userEmail)

                _foods.value = data.foodsInCart ?: listOf()
                calculateTotalPrice()
            } catch (exception: Exception) {
                Log.e("Foods", exception.stackTraceToString())
            }
        }
    }

    fun deleteFromCart(foodId: Int) {
        viewModelScope.launch {
            try {
                val userEmail = dataStoreRepo.getUserEmail()
                foodApiService.deleteFromCart(foodId, userEmail)
            } catch (exception: Exception) {
                Log.e("Foods", exception.stackTraceToString())
            } finally {
                getFoodsFromCart()
                calculateTotalPrice()
            }
        }
    }

    fun order() {
        _foods.value?.forEach {
            deleteFromCart(it.id.toInt())
        }
    }

}