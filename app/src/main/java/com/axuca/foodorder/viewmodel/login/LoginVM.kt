package com.axuca.foodorder.viewmodel.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.foodorder.repo.DataStoreRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
) : ViewModel() {

    init {
        Log.e("LoginVM", " init block")
    }

    fun handleSignInResult(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            runBlocking {
                dataStoreRepo.setUserEmail(account.email)
                dataStoreRepo.setUserName(account.displayName)
                Log.e("ProfileVM", "${account.email} : ${account.displayName}")
            }
        } catch (e: ApiException) {
            Log.w("LoginVM", "signInResult:failed code=" + e.statusCode)
            e.stackTraceToString()
        }
    }

    fun setEmail(email: String) {
        viewModelScope.launch {
            dataStoreRepo.setUserEmail(email)
        }
    }

//    fun setName(name: String){
//        viewModelScope.launch {
//            dataStoreRepo.setUserName(name)
//        }
//    }
}