package com.axuca.foodorder.viewmodel.VMProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.axuca.foodorder.repo.DataStoreRepo
import com.axuca.foodorder.viewmodel.MainVM

/**
 * Not used currently
 */
class MainVMProvider(private val repo: DataStoreRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainVM(repo) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}