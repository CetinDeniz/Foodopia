package com.axuca.foodorder.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.axuca.foodorder.adapter.FoodAdapter
import com.axuca.foodorder.adapter.FoodClickListener
import com.axuca.foodorder.databinding.FragmentRestaurantBinding
import com.axuca.foodorder.viewmodel.RestaurantVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantFragment : Fragment() {
    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    private val args: RestaurantFragmentArgs by navArgs()
    private val viewModel by viewModels<RestaurantVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = FoodClickListener {
            findNavController().navigate(
                RestaurantFragmentDirections.actionRestaurantFragmentToFoodDetail(
                    viewModel.getFoodWithId(it), args.restaurant
                )
            )
        }

        binding.apply {
            this.viewModel = this@RestaurantFragment.viewModel
            lifecycleOwner = this@RestaurantFragment
            foodRecycler.adapter = FoodAdapter(clickListener)
            restaurant = args.restaurant

            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    viewModel?.searchFood(p0)
                }

                override fun afterTextChanged(p0: Editable) {
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}