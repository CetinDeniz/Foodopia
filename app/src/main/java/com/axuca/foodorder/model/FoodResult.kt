package com.axuca.foodorder.model

import com.squareup.moshi.Json

data class FoodResult(
    @Json(name = "yemekler") val foods: List<Food>,
    val success: Int
)