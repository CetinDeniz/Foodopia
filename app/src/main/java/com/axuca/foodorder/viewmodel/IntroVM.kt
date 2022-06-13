package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.repo.DataStoreRepo
import com.axuca.foodorder.util.createRestaurants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class IntroVM @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val database: RestaurantDatabase
) : ViewModel() {

    init {
        Log.e("IntroVM", " init block")
    }

    fun endingProcess() {
        viewModelScope.launch {
            database.getRestaurantDao().addRestaurants(createRestaurants())
        }
        runBlocking {
            dataStoreRepo.setFirstLaunchToFalse()
        }
    }

}