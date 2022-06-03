package com.axuca.foodorder.view.bottom_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.adapter.CartAdapter
import com.axuca.foodorder.adapter.CartItemAddClickListener
import com.axuca.foodorder.adapter.CartItemDeleteClickListener
import com.axuca.foodorder.databinding.FragmentCartBinding
import com.axuca.foodorder.viewmodel.CartVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel by viewModels<CartVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFoodsFromCart()

        val addClickListener = CartItemAddClickListener {
//            viewModel.cartAddItem()
        }

        val deleteClickListener = CartItemDeleteClickListener{
            viewModel.deleteFromCart(it)
        }

        binding.apply {
            viewModel = this@CartFragment.viewModel
            lifecycleOwner = this@CartFragment
            cartRecycler.adapter = CartAdapter(addClickListener,deleteClickListener)

            orderButton.setOnClickListener {
                this@CartFragment.viewModel.order()
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToHomeFragment())
            }
        }
    }


}