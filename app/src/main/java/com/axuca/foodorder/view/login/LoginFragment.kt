package com.axuca.foodorder.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.axuca.foodorder.R
import com.axuca.foodorder.injection.userEmailKey
import com.axuca.foodorder.injection.userNameKey
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var startActivityForResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()

        startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    handleSignInResult(task)
                }
            }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.create_account).setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        view.findViewById<TextView>(R.id.forgot_password).setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            val emailView = view.findViewById<TextInputEditText>(R.id.email_edit_text)
            val emailLayout = view.findViewById<TextInputLayout>(R.id.email_input_layout)
            val passwordLayout = view.findViewById<TextInputLayout>(R.id.password_input_layout)
            val passwordView = view.findViewById<TextInputEditText>(R.id.password_again_edit_text)

            val email = emailView.text.toString()
            val password = passwordView.text.toString()

            if (TextUtils.isEmpty(email)) {
                emailLayout.error = "Email cannot be empty"
            } else if (TextUtils.isEmpty(password)) {
                passwordLayout.error = "Password cannot be empty"
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) {
                        if (it.isSuccessful) {
                            Snackbar.make(
                                view,
                                "User logged in successfully",
                                Snackbar.LENGTH_SHORT
                            ).show()

                            runBlocking {
                                dataStore.edit { mutablePreferences ->
                                    mutablePreferences[userEmailKey] = email
//                                    mutablePreferences[userNameKey] =  For this i need to do firebase call for getting name associated with email
                                }
                            }
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        } else {
                            Snackbar.make(
                                view,
                                "Log in error ${it.exception?.message ?: "Null exception"}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        view.findViewById<SignInButton>(R.id.signInButton2).setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            runBlocking {
                dataStore.edit {
                    it[userEmailKey] = account?.email ?: "Empty Email"
                    it[userNameKey] = account?.displayName ?: "Empty Name"
                }
            }

            // Signed in successfully, show authenticated UI.
            // updateUI(account)
            Snackbar.make(
                requireView(),
                "User logged in successfully",
                Snackbar.LENGTH_SHORT
            ).show()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Login Fragment", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }
}