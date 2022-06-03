package com.axuca.foodorder.viewmodel.VMProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.viewmodel.HomeVM

/**
 * Not used currently
 */
class HomeVMProvider(private val database: RestaurantDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeVM::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeVM(database) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}