package com.axuca.foodorder.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepo @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    private val firstLaunch: Flow<Boolean> = dataStore.data.map {
        it[FIRST_LAUNCH_KEY] ?: true
    }

    suspend fun isFirstLaunch(): Boolean {
        return firstLaunch.first()
    }

    suspend fun setFirstLaunchToFalse() {
        dataStore.edit { it[FIRST_LAUNCH_KEY] = false }
    }

    suspend fun getUserName(): String {
        return dataStore.data.first()[USER_NAME_KEY] ?: "Null Name"
    }

    suspend fun setUserName(name: String?) {
        dataStore.edit { it[USER_NAME_KEY] = name ?: "Null Name" }
    }

    suspend fun getUserEmail(): String {
        return dataStore.data.first()[USER_EMAIL_KEY] ?: "Null Email"
    }

    suspend fun setUserEmail(email: String?) {
        dataStore.edit { it[USER_EMAIL_KEY] = email ?: "Null Email" }
    }

}