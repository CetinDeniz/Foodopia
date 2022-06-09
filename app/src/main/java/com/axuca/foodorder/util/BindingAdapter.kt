package com.axuca.foodorder.util

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.axuca.foodorder.R
import com.axuca.foodorder.adapter.CartAdapter
import com.axuca.foodorder.adapter.FoodAdapter
import com.axuca.foodorder.model.network.CartFoodItem
import com.axuca.foodorder.model.network.Food

private const val IMAGE_BASE_URL = "http://kasimadalan.pe.hu/yemekler/resimler/"
private const val DETAIL_IMAGE_URL = "http://kasimadalan.pe.hu/yemekresimler/"

@BindingAdapter("imageResource")
fun ImageView.setImage(@DrawableRes drawableRes: Int) {
//    setImageResource(drawableRes)
    load(drawableRes) {
        placeholder(R.drawable.loading_animation)
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageName: String) {
    load("$IMAGE_BASE_URL$imageName") {
        placeholder(R.drawable.loading_animation)
        error(R.drawable.broken_image)
    }
}

@BindingAdapter("loadBigImage")
fun ImageView.loadBigImage(imageName: String) {
    load("$DETAIL_IMAGE_URL${if (imageName == "kofte.png") "izgarakofte.png" else imageName}") {
        placeholder(R.drawable.loading_animation)
        error(R.drawable.broken_image)
    }
}

@BindingAdapter("submitList")
fun RecyclerView.setFoodAdapterData(foods: List<Food>?) {
    foods?.let {
        (adapter as FoodAdapter).submitList(foods)
    }
}

@BindingAdapter("submitCartList")
fun RecyclerView.setCartAdapterData(foods: List<CartFoodItem>?) {
    foods?.let {
        (adapter as CartAdapter).submitList(foods)
    }
}

/** Not working ? */
@BindingAdapter("recyclerEmptyImage")
fun LinearLayout.setLayoutVisibility(foodList: List<CartFoodItem>?) {
    visibility = if (foodList.isNullOrEmpty()) {
        View.VISIBLE
    } else View.GONE
}

@BindingAdapter("totalPrice")
fun TextView.setTotalPriceText(totalPrice: Int?) {
    text = resources.getString(R.string.cart_total_price, totalPrice ?: 0)
}