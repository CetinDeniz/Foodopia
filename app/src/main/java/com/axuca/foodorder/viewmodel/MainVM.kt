package com.axuca.foodorder.viewmodel

import androidx.lifecycle.ViewModel
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val repo: DataStoreRepo) : ViewModel() {

    private var isReady = false
    private var firstLaunch: Boolean = true

    init {
        runBlocking {
            firstLaunch = repo.isFirstLaunch()
        }
    }

    /** AppIntro slider for time first time app launch */
    fun isFirstLaunch() = firstLaunch

    /** For splash screen visibility */
    fun isReady() = isReady
    fun setReady() {
        isReady = true
    }
}