package com.axuca.foodorder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.model.network.Food
import com.axuca.foodorder.network.FoodApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantVM @Inject constructor(private val foodApiService: FoodApiService) : ViewModel() {

    private lateinit var immutableFoods: List<Food>

    private var _allFoods = MutableLiveData<List<Food>>()

    val allFoods: LiveData<List<Food>>
        /** Adapter observing */
        get() = _allFoods

    init {
        initiliazeData()
    }

    private fun initiliazeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = foodApiService.getAllFoods().foods
            withContext(Dispatchers.Main) {
                immutableFoods = result
                _allFoods.value = result
            }
        }
    }

    fun getFoodWithId(foodId: Int): Food {
        return _allFoods.value!!.first {
            it.id == foodId
        }
    }


    fun searchFood(char: CharSequence) {
        _allFoods.value = immutableFoods.filter {
            it.name.contains(char, ignoreCase = true)
        }
    }

}