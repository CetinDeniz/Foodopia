package com.axuca.foodorder.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.databinding.FragmentLoginBinding
import com.axuca.foodorder.viewmodel.login.LoginVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var startActivityForResult: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<LoginVM>()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        startActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    viewModel.handleSignInResult(it.data)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            createAccount.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            forgotPassword.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
            }

            signInButton.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordAgainEditText.text.toString()

                if (TextUtils.isEmpty(email)) {
                    binding.emailInputLayout.error = "Email cannot be empty"
                } else if (TextUtils.isEmpty(password)) {
                    binding.passwordInputLayout.error = "Password cannot be empty"
                } else {
                    signInWithEmailAndPassword(email, password)
                }
            }

            googleSignIn.setOnClickListener { signInWithGoogle() }
        }
    }

    /** For Sign-in with Email-Password */
    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "User logged in successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.setEmail(email)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                } else {
                    Snackbar.make(
                        binding.root,
                        "Log in error ${it.exception?.message ?: "Null exception"}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /** For Google Sign-in */
    private fun signInWithGoogle() {
        startActivityForResult.launch(getSignInIntent())
    }

    private fun getSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        return mGoogleSignInClient.signInIntent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}