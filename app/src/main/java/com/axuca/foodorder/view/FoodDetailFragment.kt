package com.axuca.foodorder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.axuca.foodorder.R
import com.axuca.foodorder.databinding.FragmentFoodDetailBinding
import com.axuca.foodorder.viewmodel.FoodDetailVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailFragment : Fragment() {
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private val args: FoodDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<FoodDetailVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            food = args.food
            restaurant = args.restaurant

            add.setOnClickListener {
                val result = quantity.text.toString().toInt() + 1
                quantity.text = result.toString()
                totalPrice.text = getString(R.string.price, result * args.food.price)
            }
            delete.setOnClickListener {
                val result = quantity.text.toString().toInt()
                if (result > 1) {
                    quantity.text = (result - 1).toString()
                    totalPrice.text = getString(R.string.price, (result - 1) * args.food.price)
                }
            }

            addToCart.setOnClickListener {
                /** Sign-in with email */
//                val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                /** Google Sign-In */
//                val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
                viewModel.addToCart(
                    args.food.name,
                    args.food.imageName,
                    args.food.price,
                    binding.quantity.text.toString().toInt()
                )
                findNavController().navigate(
                    FoodDetailFragmentDirections.actionFoodDetailFragmentToRestaurantFragment(
                        args.restaurant
                    )
                )
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}