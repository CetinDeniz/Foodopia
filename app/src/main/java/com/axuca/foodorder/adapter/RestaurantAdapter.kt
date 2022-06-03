package com.axuca.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.axuca.foodorder.databinding.HorizontalRestaurantListItemBinding
import com.axuca.foodorder.databinding.VerticalRestaurantListItemBinding
import com.axuca.foodorder.model.Restaurant

class RestaurantAdapter(
    private val clickListener: RestaurantClickListener,
    private val isHorizontal: Boolean,
    private val restaurants: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant, clickListener: RestaurantClickListener) {
            if (isHorizontal) {
                with((binding as HorizontalRestaurantListItemBinding)) {
                    this.restaurant = restaurant
                    this.clickListener = clickListener
                    executePendingBindings()
                }
            } else {
                with((binding as VerticalRestaurantListItemBinding)) {
                    this.restaurant = restaurant
                    this.clickListener = clickListener
                    executePendingBindings()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(
            if (isHorizontal) {
                HorizontalRestaurantListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            } else {
                VerticalRestaurantListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        )
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position], clickListener)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}

class RestaurantClickListener(val clickListener: (restaurantId: Int) -> Unit) {
    fun onClick(restaurantId: Int) = clickListener(restaurantId)
}