package com.axuca.foodorder.viewmodel.bottom_navigation

import androidx.lifecycle.ViewModel
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.model.db.Restaurant
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
//    private val flpc: FusedLocationProviderClient
) : ViewModel() {

    private lateinit var restaurants: List<Restaurant>

    init {
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

    fun getSortedRestaurants(
        latitude: Double = 38.489950139953976,
        longitude: Double = 27.083443465917625
    ): List<Restaurant> {
        return restaurants.sortByDistance(latitude, longitude)
    }

    private fun List<Restaurant>.sortByDistance(
        latitude: Double,
        longitude: Double
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
        return earthRadius * c; // Distance in km
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