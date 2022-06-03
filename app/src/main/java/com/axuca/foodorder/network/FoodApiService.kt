package com.axuca.foodorder.network

import com.axuca.foodorder.model.AddCartResult
import com.axuca.foodorder.model.CartResult
import com.axuca.foodorder.model.Food
import com.axuca.foodorder.model.FoodResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://kasimadalan.pe.hu/yemekler/"

private const val ALL_FOODS = "tumYemekleriGetir.php"
private const val CART_FOODS = "sepettekiYemekleriGetir.php"
private const val ADD_FOOD = "sepeteYemekEkle.php"
private const val DELETE_FOOD = "sepettenYemekSil.php"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface FoodApiService {
    /**
     * Returns a Coroutine [List] of [Food] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "ALL_FOODS" endpoint will be requested with the GET HTTP method
     */
    @GET(ALL_FOODS)
    suspend fun getAllFoods(): FoodResult

    @POST(ADD_FOOD)
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("yemek_adi") foodName: String,
        @Field("yemek_resim_adi") foodImageName: String,
        @Field("yemek_fiyat") foodPrice: Int,
        @Field("yemek_siparis_adet") foodOrderQuantity: Int,
        @Field("kullanici_adi") email: String
    ): AddCartResult

    @POST(CART_FOODS) // "sepettekiYemekleriGetir.php"
    @FormUrlEncoded
    suspend fun getFromCart(
        @Field("kullanici_adi") email: String
    ): CartResult

    @POST(DELETE_FOOD) // "sepettenYemekSil.php"
    @FormUrlEncoded
    suspend fun deleteFromCart(
        @Field("sepet_yemek_id") foodId: Int,
        @Field("kullanici_adi") userEmail: String
    ): CartResult
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object FoodApi {
    val retrofitService: FoodApiService by lazy { retrofit.create(FoodApiService::class.java) }
}