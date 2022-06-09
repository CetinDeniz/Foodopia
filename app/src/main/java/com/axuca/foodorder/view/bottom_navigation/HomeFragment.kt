package com.axuca.foodorder.view.bottom_navigation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeVM>()

    @Inject
    lateinit var flpc: FusedLocationProviderClient

    private lateinit var locationTask: Task<Location>
    private lateinit var location: Pair<Double, Double>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = RestaurantClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRestaurantFragment(
                    viewModel.getSpecificRestaurant(it)
                )
            )
        }

        runBlocking {
            initiliazeLocation()
        }

        with(binding) {
            nearbyRecycler.setHasFixedSize(true)
            popularRecycler.setHasFixedSize(true)
            allRecycler.setHasFixedSize(true)

            if (!::location.isInitialized) {
                Log.e("Location pair", " Not initiliazed")
            }
            var adapter = RestaurantAdapter(
                clickListener,
                true,
                viewModel.getSortedRestaurants(
                    38.489950139953976,
                    27.083443465917625
                ) // App crashing in here location.first, location.second
            )
            nearbyRecycler.adapter = adapter

            adapter = RestaurantAdapter(clickListener, true, viewModel.getPopularRestaurants())
            binding.popularRecycler.adapter = adapter

            adapter = RestaurantAdapter(clickListener, false, viewModel.getRestaurants())
            allRecycler.adapter = adapter
        }
    }

    private fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initiliazeLocation() {
        if (permissionGranted()) {
//            locationTask = flpc.lastLocation
//            locationTask.addOnSuccessListener {
//                location = Pair(it.latitude, it.longitude)
//            }
        } else {
            location = Pair(38.489950139953976, 27.083443465917625)
        }
    }

}