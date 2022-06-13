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

    /** With giving false value at the start, empty view is not shown immediately */
    private val _emptyCart = MutableLiveData(false)
    val emptyCart: LiveData<Boolean> get() = _emptyCart

    private lateinit var userEmail: String

    init {
        Log.e("CartVM", " init block")
        viewModelScope.launch {
            userEmail = dataStoreRepo.getUserEmail()
            getFoodsFromCart()
        }
    }

    fun getFoodsFromCart() {
        viewModelScope.launch {
            try {
                _foods.value = foodApiService.getFromCart(userEmail).foodsInCart
                Log.e("getFoodsFromCart", _foods.value!!.size.toString())
            } catch (exception: Exception) {
                /** Exception catches when last item deleted */
                Log.e("getFoodsFromCart", exception.stackTraceToString())
                _foods.value = listOf()
            } finally {
                isCartEmpty()
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

    private fun calculateTotalPrice() {
        _totalPrice.value = _foods.value?.sumOf {
            it.orderQuantity.toInt() * it.price.toInt()
        } ?: 0
    }

    private fun isCartEmpty() {
        _emptyCart.value = _foods.value?.isEmpty()
    }

}