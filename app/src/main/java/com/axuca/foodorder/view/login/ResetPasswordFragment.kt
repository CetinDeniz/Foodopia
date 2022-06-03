package com.axuca.foodorder.view.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.reset).setOnClickListener {
            val emailView = view.findViewById<EditText>(R.id.reset_password_edit_text)
            val email = emailView.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                emailView.error = "Email cannot be empty"
                emailView.requestFocus()
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailView.error = "Email does not match pattern."
                emailView.requestFocus()
            }else{
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                        Snackbar.make(
                            view,
                            "Check your email to reset your password!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }else{
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