package com.axuca.foodorder.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.axuca.foodorder.injection.firstLaunchKey
import com.axuca.foodorder.injection.userEmailKey
import com.axuca.foodorder.injection.userNameKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepo @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val firstLaunch: Flow<Boolean> = dataStore.data.map {
        it[firstLaunchKey] ?: true
    }

    suspend fun isFirstLaunch(): Boolean {
        return firstLaunch.first()
    }

    suspend fun getUserName(): String {
        return dataStore.data.first()[userNameKey] ?: "No Name"
    }

    suspend fun getUserEmail(): String {
        return dataStore.data.first()[userEmailKey] ?: "No Name"
    }

}