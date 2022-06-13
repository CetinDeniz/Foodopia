package com.axuca.foodorder.viewmodel.bottom_navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.model.db.Restaurant
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class HomeVM @Inject constructor(
    private val database: RestaurantDatabase,
    private val fsc: FusedLocationProviderClient
) : ViewModel() {

    private var restaurants: List<Restaurant>
    private lateinit var location: Pair<Double, Double>

    init {
        Log.e("HomeVM", " init block")
        runBlocking {
            restaurants = database.getRestaurantDao().getAllRestaurants()
        }
    }

    fun getRestaurants(): List<Restaurant> {
        return restaurants
    }

    fun getPopularRestaurants(): List<Restaurant> {
        return restaurants.sortedByDescending {
            it.popularity
        }
    }

//    private suspend fun initializeLocation() {
//        fsc.lastLocation.addOnSuccessListener {
//            it?.let { loc ->
//                location = Pair(loc.latitude, loc.longitude)
//            }
//        }.await().wait()
//    }

    fun getSortedRestaurants(isPermissionGranted: Boolean): List<Restaurant> {
//        viewModelScope.launch {
//            try {
//                if (isPermissionGranted) {
//                    initializeLocation() /** Need to work as a sync */
//                } else {
//                    location = Pair(38.489950139953976, 27.083443465917625)
//                }
//
//            } catch (exception: Exception) {
//
//            }
//        }
//        return restaurants.sortByDistance(location.first, location.second)
        return restaurants.sortByDistance()
    }

    private fun List<Restaurant>.sortByDistance(
        latitude: Double = 38.489950139953976,
        longitude: Double = 27.083443465917625
    ): List<Restaurant> {
        return sortedBy {
            getDistanceFromLatLonInKm(
                latitude,
                longitude,
                it.locationLatitude,
                it.locationLongitude
            )
        }
    }

    private fun getDistanceFromLatLonInKm(
        userLatitude: Double,
        userLongitude: Double,
        latitudeSecond: Double,
        longitudeSecond: Double
    ): Double {
        val earthRadius = 6371

        val dLat = deg2rad(latitudeSecond - userLatitude)  // deg2rad below
        val dLon = deg2rad(longitudeSecond - userLongitude)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(deg2rad(userLatitude)) * cos(deg2rad(latitudeSecond)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c // Distance in km
    }

    private fun deg2rad(deg: Double): Double {
        return deg * (Math.PI / 180)
    }

    fun getSpecificRestaurant(id: Int): Restaurant {
        return restaurants.first {
            it.id == id
        }
    }
}