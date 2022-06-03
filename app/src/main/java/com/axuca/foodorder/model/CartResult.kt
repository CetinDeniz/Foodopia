package com.axuca.foodorder.model

import com.squareup.moshi.Json

data class CartResult(
    @Json(name = "sepet_yemekler") var foodsInCart: List<CartFoodItem>?,
    @Json(name = "success") var success: Int
)