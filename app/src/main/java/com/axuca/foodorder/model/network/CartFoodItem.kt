package com.axuca.foodorder.model.network

import com.squareup.moshi.Json

data class CartFoodItem(
    @Json(name = "sepet_yemek_id") val id: String,
    @Json(name = "yemek_adi") val name: String,
    @Json(name = "yemek_resim_adi") val imageName: String,
    @Json(name = "yemek_fiyat") val price: String,
    @Json(name = "yemek_siparis_adet") val orderQuantity: String,
    @Json(name = "kullanici_adi") val email: String
)