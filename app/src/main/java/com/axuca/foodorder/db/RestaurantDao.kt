package com.axuca.foodorder.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.axuca.foodorder.model.db.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    suspend fun getAllRestaurants(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRestaurants(restaurant: List<Restaurant>)
}