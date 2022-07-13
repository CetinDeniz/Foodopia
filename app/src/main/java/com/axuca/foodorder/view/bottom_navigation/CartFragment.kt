package com.axuca.foodorder.view.bottom_navigation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.R
import com.axuca.foodorder.adapter.CartAdapter
import com.axuca.foodorder.adapter.CartItemAddClickListener
import com.axuca.foodorder.adapter.CartItemDeleteClickListener
import com.axuca.foodorder.databinding.FragmentCartBinding
import com.axuca.foodorder.viewmodel.bottom_navigation.CartVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFoodsFromCart()

        /** Gonna implement later */
        val addClickListener = CartItemAddClickListener {
//            viewModel.cartAddItem()
        }
        val deleteClickListener = CartItemDeleteClickListener {
            viewModel.deleteFromCart(it)
        }

        binding.apply {
            viewModel = this@CartFragment.viewModel
            lifecycleOwner = this@CartFragment
            cartRecycler.adapter = CartAdapter(addClickListener, deleteClickListener)

            orderButton.setOnClickListener {
                showOrderDialog()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showOrderDialog() {
        val dialog = Dialog(requireActivity()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.order_dialog)
        }

        /** Button Click listener */
        dialog.findViewById<Button>(R.id.close).setOnClickListener {
            this@CartFragment.viewModel.order()
            dialog.dismiss()
            findNavController().navigate(R.id.action_global_homeFragment)
        }
        dialog.show()
    }

}