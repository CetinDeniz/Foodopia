package com.axuca.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.axuca.foodorder.databinding.FoodListItemBinding
import com.axuca.foodorder.model.Food

class FoodAdapter(
    private val clickListener: FoodClickListener,
//    private val foods: List<Food>
) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffUtilCallback) {

    inner class FoodViewHolder(private val binding: FoodListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food, clickListener: FoodClickListener) {
            with(binding) {
                this.food = food
                this.clickListener = clickListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            FoodListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}

class FoodClickListener(val clickListener: (foodId: Int) -> Unit) {
    fun onClick(foodId: Int) = clickListener(foodId)
}