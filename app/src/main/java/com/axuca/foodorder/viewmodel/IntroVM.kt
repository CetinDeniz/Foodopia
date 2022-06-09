package com.axuca.foodorder.viewmodel

import androidx.lifecycle.ViewModel
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.repo.DataStoreRepo
import com.axuca.foodorder.util.createRestaurants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class IntroVM @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val database: RestaurantDatabase
) : ViewModel() {

    fun endingProcess() {
//        viewModelScope.launch {
        runBlocking {
            database.getRestaurantDao().addRestaurants(createRestaurants())
            dataStoreRepo.setFirstLaunchToFalse()
        }
    }
}