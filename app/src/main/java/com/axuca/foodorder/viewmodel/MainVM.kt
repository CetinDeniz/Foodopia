package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val repo: DataStoreRepo) : ViewModel() {

    private var isReady: Boolean = false
    private var firstLaunch: Boolean = true

    init {
        Log.e("HomeVM", isReady.toString())

        runBlocking {
            firstLaunch = repo.isFirstLaunch()
        }

        isReady = true
        Log.e("HomeVM", isReady.toString())
    }

    /** AppIntro slider for time first time app launch */
    fun isFirstLaunch() = firstLaunch

    /** For splash screen visibility */
    fun isReady() = isReady
}