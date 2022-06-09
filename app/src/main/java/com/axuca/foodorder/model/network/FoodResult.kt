package com.axuca.foodorder.model.network

import com.squareup.moshi.Json

data class FoodResult(
    @Json(name = "yemekler") val foods: List<Food>,
    val success: Int
)