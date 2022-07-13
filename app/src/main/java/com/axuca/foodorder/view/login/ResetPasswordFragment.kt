package com.axuca.foodorder.view.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.R
import com.axuca.foodorder.databinding.FragmentResetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    @Inject
    lateinit var mAuth: FirebaseAuth

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            reset.setOnClickListener {
                val email = resetPasswordEditText.text.toString().trim()

                if (TextUtils.isEmpty(email)) {
                    emailInputLayout.error = "Email cannot be empty"
                    emailInputLayout.requestFocus()
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInputLayout.error = "Email does not match pattern."
                    emailInputLayout.requestFocus()
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
//                            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                            findNavController().navigate(R.id.action_global_loginFragment)
                            Snackbar.make(
                                view,
                                "Check your email to reset your password!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            Snackbar.make(
                                view,
                                "Try again! Something wrong happened!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}