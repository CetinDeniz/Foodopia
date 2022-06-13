package com.axuca.foodorder.view.bottom_navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.adapter.RestaurantAdapter
import com.axuca.foodorder.adapter.RestaurantClickListener
import com.axuca.foodorder.databinding.FragmentHomeBinding
import com.axuca.foodorder.viewmodel.bottom_navigation.HomeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("HomeFragment", " onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("HomeFragment", " onViewCreated")

        val clickListener = RestaurantClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRestaurantFragment(
                    viewModel.getSpecificRestaurant(it)
                )
            )
        }

        binding.apply {
            nearbyRecycler.setHasFixedSize(true)
            popularRecycler.setHasFixedSize(true)
            allRecycler.setHasFixedSize(true)

            val restaurants = viewModel.getSortedRestaurants(isPermissionGranted())

            var adapter = RestaurantAdapter(
                clickListener,
                true,
                restaurants
            )
            nearbyRecycler.adapter = adapter

            adapter = RestaurantAdapter(clickListener, true, viewModel.getPopularRestaurants())
            binding.popularRecycler.adapter = adapter

            adapter = RestaurantAdapter(clickListener, false, viewModel.getRestaurants())
            allRecycler.adapter = adapter
        }
    }

    private fun isPermissionGranted(): Boolean {
        val context = requireContext()
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}