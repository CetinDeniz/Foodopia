package com.axuca.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.axuca.foodorder.repo.DataStoreRepo
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: DataStoreRepo,
    private val mAuth: FirebaseAuth,
    private val googleAccount: GoogleSignInAccount?
) : ViewModel() {

    private var isReady = false
    private var firstLaunch: Boolean = true

    init {
        Log.e("MainVM", " init block")
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

    fun isUserSignedIn(): Boolean {
        /** Sign-in with email */
        val currentUser: FirebaseUser? = mAuth.currentUser
        /** Google Sign-In */
        googleAccount

        return currentUser == null && googleAccount == null
    }
}