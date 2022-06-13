package com.axuca.foodorder.viewmodel.bottom_navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(private val dataStoreRepo: DataStoreRepo) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    init {
        Log.e("ProfileVM", " init block")
        viewModelScope.launch {
            _userName.value = dataStoreRepo.getUserName()
            _userEmail.value = dataStoreRepo.getUserEmail()
        }
        Log.e("ProfileVM", "${userName.value} : ${userEmail.value}")
    }

    fun resetUserInformation() {
        viewModelScope.launch {
            dataStoreRepo.setUserEmail(null)
            dataStoreRepo.setUserName(null)
        }

    }
}