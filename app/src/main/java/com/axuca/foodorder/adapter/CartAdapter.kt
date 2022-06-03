package com.axuca.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.axuca.foodorder.adapter.CartAdapter.CartItemViewHolder
import com.axuca.foodorder.databinding.CartFoodListItemBinding
import com.axuca.foodorder.model.CartFoodItem

class CartAdapter(
    private val addClickListener: CartItemAddClickListener,
    private val deleteClickListener: CartItemDeleteClickListener
) : ListAdapter<CartFoodItem, CartItemViewHolder>(CartDiffUtilCallback) {

    inner class CartItemViewHolder(private val binding: CartFoodListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cartFoodItem: CartFoodItem,
            deleteClickListener: CartItemDeleteClickListener,
            addClickListener: CartItemAddClickListener
        ) {
            with(binding) {
                this.cartFoodItem = cartFoodItem
                this.deleteClickListener = deleteClickListener
                this.addClickListener = addClickListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CartFoodListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position), deleteClickListener, addClickListener)
    }
}

private object CartDiffUtilCallback : DiffUtil.ItemCallback<CartFoodItem>() {
    override fun areItemsTheSame(oldItem: CartFoodItem, newItem: CartFoodItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartFoodItem, newItem: CartFoodItem): Boolean {
        return oldItem == newItem
    }
}

class CartItemDeleteClickListener(val clickListener: (foodId: Int) -> Unit) {
    fun onClick(foodId: Int) = clickListener(foodId)
}

class CartItemAddClickListener(val clickListener: (foodId: Int) -> Unit) {
    fun onClick(foodId: Int) = clickListener(foodId)
}