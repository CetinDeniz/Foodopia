package com.axuca.foodorder.model.db

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * This will be stored in Room DB
 */
@Parcelize
@Entity(tableName = "restaurant")
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /** Room will handle */

    val name: String,

    // val foods: List<Food>, /** This may not be needed */

    @ColumnInfo(name = "drawable_res")
    @DrawableRes val drawableRes: Int,

    @ColumnInfo(name = "opening_hour") val openingHour: String,
    @ColumnInfo(name = "closing_hour") val closingHour: String,

    @ColumnInfo(name = "estimated_delivery_minute") val estimatedDeliveryMinute: String,

    @ColumnInfo(name = "minimum_order_amount") val minimumOrderAmount: Int,

    val star: Float,
    @ColumnInfo(name = "vote_count") val voteCount: Int,

    /** Popular restaurants */
    val popularity: Double = (star * voteCount).toDouble(),


    /** For suggesting "nearby restaurants" */
//  val location: Location
    @ColumnInfo(name = "location_latitude")
    val locationLatitude: Double,
    @ColumnInfo(name = "location_longitude")
    val locationLongitude: Double
) : Parcelable