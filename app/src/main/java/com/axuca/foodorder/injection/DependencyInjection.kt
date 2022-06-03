package com.axuca.foodorder.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.network.FoodApi
import com.axuca.foodorder.network.FoodApiService
import com.axuca.foodorder.repo.DataStoreRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"
val firstLaunchKey = booleanPreferencesKey("first_launch")
val userNameKey = stringPreferencesKey("user_name")
val userEmailKey = stringPreferencesKey("user_email")

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DataStoreRepo {

    @Singleton
    @Provides
    fun provideDataStoreRepo(dataStore: DataStore<Preferences>): DataStoreRepo {
        return DataStoreRepo(dataStore)
    }
}


@InstallIn(SingletonComponent::class)
@Module
object RestaurantDatabaseModule {

    @Singleton
    @Provides
    fun provideRestaurantDatabase(@ApplicationContext appContext: Context): RestaurantDatabase {
        return RestaurantDatabase.getDatabase(appContext)
    }
}

@InstallIn(SingletonComponent::class)
@Module
object FoodApiServiceModule {

    @Singleton
    @Provides
    fun provideFoodApiService(): FoodApiService {
        return FoodApi.retrofitService
    }
}

@InstallIn(SingletonComponent::class)
@Module
object FusedLocationProviderModule {

    @Singleton
    @Provides
    fun provideFusedLocationProvider(@ApplicationContext appContext: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }
}

