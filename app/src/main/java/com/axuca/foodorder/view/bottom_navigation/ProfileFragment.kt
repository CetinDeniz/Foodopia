package com.axuca.foodorder.view.bottom_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.databinding.FragmentProfileBinding
import com.axuca.foodorder.injection.userEmailKey
import com.axuca.foodorder.injection.userNameKey
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserNameAndEmail()

        /** Google Sign-In Account */
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        binding.signOut.setOnClickListener {
            if (account == null) {
                FirebaseAuth.getInstance().signOut()
            } else {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                GoogleSignIn.getClient(requireActivity(), gso).signOut()
            }
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

    private fun getUserNameAndEmail() {
        lifecycleScope.launch {
            val dataStore = dataStore.data.first()
            binding.userName.text = dataStore[userNameKey]
            binding.email.text = dataStore[userEmailKey]
        }
    }

}