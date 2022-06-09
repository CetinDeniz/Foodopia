package com.axuca.foodorder.viewmodel.bottom_navigation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(private val dataStoreRepo: DataStoreRepo) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName get() = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail get() = _userEmail

    init {
        runBlocking {
            _userName.value = dataStoreRepo.getUserName()
            _userEmail.value = dataStoreRepo.getUserEmail()
        }
        Log.e("ProfileVM", "${userName.value} : ${userEmail.value}")
//        viewModelScope.launch {
//            _userName.value = dataStoreRepo.getUserName()
//            _userEmail.value = dataStoreRepo.getUserEmail()
//            Log.e("ProfileVM", "${userName.value} : ${userEmail.value}")
//        }
    }

    fun resetUserInformation(){
        viewModelScope.launch {
            dataStoreRepo.setUserEmail(null)
            dataStoreRepo.setUserName(null)
        }

    }
}