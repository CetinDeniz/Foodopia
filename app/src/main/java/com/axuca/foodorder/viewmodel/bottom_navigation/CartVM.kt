package com.axuca.foodorder.viewmodel.bottom_navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.model.network.CartFoodItem
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

    private lateinit var userEmail: String

    init {
        viewModelScope.launch {
            userEmail = dataStoreRepo.getUserEmail()
        }
        getFoodsFromCart()
    }

    private fun calculateTotalPrice() {
        _totalPrice.value = _foods.value?.sumOf {
            it.orderQuantity.toInt() * it.price.toInt()
        } ?: 0
    }

    fun getFoodsFromCart() {
        viewModelScope.launch {
            try {
                _foods.value = foodApiService.getFromCart(userEmail).foodsInCart
                Log.e("getFoodsFromCart", _foods.value!!.size.toString())
            } catch (exception: Exception) {
                Log.e("getFoodsFromCart", exception.stackTraceToString())
                /** Throwing exception when last item deleted */
                _foods.value = listOf()
            } finally {
                calculateTotalPrice()
            }
        }
    }

    fun deleteFromCart(foodId: Int) {
        viewModelScope.launch {
            try {
                foodApiService.deleteFromCart(foodId, userEmail)
            } catch (exception: Exception) {
                Log.e("deleteFromCart", exception.stackTraceToString())
            } finally {
                getFoodsFromCart()
            }
        }
    }

    fun order() {
        _foods.value?.forEach {
            deleteFromCart(it.id.toInt())
        }
    }

}