package com.axuca.foodorder.view.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.R
import com.axuca.foodorder.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseDatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase =
            FirebaseDatabase.getInstance("https://foodorder-8022a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users")
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.login_direct_text).setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        view.findViewById<Button>(R.id.register_button).setOnClickListener {

            val nameSurNameView = view.findViewById<TextInputEditText>(R.id.name_surname)
            val emailView = view.findViewById<TextInputEditText>(R.id.email_edit_text)

            val passwordView = view.findViewById<TextInputEditText>(R.id.password_edit_text)
            val passwordAgainView =
                view.findViewById<TextInputEditText>(R.id.password_again_edit_text)

            val nameSurName = nameSurNameView.text.toString()
            val email = emailView.text.toString()
            val password = passwordView.text.toString()
            val passwordAgain = passwordAgainView.text.toString()

            if (TextUtils.isEmpty(email)) {
                emailView.error = "Email cannot be empty"
                emailView.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailView.error = "Email does not match pattern."
                emailView.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                passwordView.error = "Password cannot be empty"
                passwordView.requestFocus()
            } else if (TextUtils.isEmpty(passwordAgain)) {
                passwordView.error = "Password cannot be empty"
                passwordView.requestFocus()
            } else if (password != passwordAgain) {
                passwordAgainView.error = "Passwords are not the same"
                passwordAgainView.requestFocus()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = User(nameSurName, email, password)

                        mFirebaseDatabase.child(
                            mAuth.currentUser!!.uid
                        ).setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Snackbar.make(
                                    view,
                                    "User registered successfully",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                mAuth.signOut()
                                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                            } else {
                                Snackbar.make(
                                    view,
                                    "Registration error ${it.exception?.message ?: "Null exception"}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Snackbar.make(
                            view,
                            "Registration error ${it.exception?.message ?: "Null exception"}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}