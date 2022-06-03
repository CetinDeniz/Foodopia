package com.axuca.foodorder.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.axuca.foodorder.R
import com.axuca.foodorder.databinding.FragmentFoodDetailBinding
import com.axuca.foodorder.viewmodel.FoodDetailVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private val args: FoodDetailFragmentArgs by navArgs()
    private val foodDetailVM by viewModels<FoodDetailVM>()
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
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
                totalPrice.text = getString(R.string.price,result * args.food.price)
            }
            delete.setOnClickListener {
                val result = quantity.text.toString().toInt()
                if (result > 1) {
                    quantity.text = (result - 1).toString()
                    totalPrice.text = getString(R.string.price,(result - 1) * args.food.price)
                }
            }

            addToCart.setOnClickListener {
                /** Sign-in with email */
                val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                /** Google Sign-In */
                val account: GoogleSignInAccount? =
                    GoogleSignIn.getLastSignedInAccount(requireContext())

                foodDetailVM.addToCart(
                    args.food.name,
                    args.food.imageName,
                    args.food.price,
                    binding.quantity.text.toString().toInt(),
//                    dataStore.data.first()[userEmailKey]!!
                    if (account == null) {
                        Log.e("addToCart", currentUser?.email ?: "Null google account email")
                        currentUser?.email ?: "Null google account email"
                    } else {
                        Log.e("addToCart", currentUser?.email ?: "Null user email")
                        account.email ?: "Null user email"
                    }
                )
                findNavController().navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToRestaurantFragment(args.restaurant))
            }

        }
    }

}