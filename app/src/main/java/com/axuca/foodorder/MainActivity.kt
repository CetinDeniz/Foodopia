package com.axuca.foodorder

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.axuca.foodorder.intro.Intro
import com.axuca.foodorder.viewmodel.MainVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainVM>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("onCreate : ", "before installSplashScreen")
        /** This must be execute before the onCreate and setContentView */
        installSplashScreen().apply {
            setOnExitAnimationListener { splashScreenViewProvider ->
                val slideBack = ObjectAnimator.ofFloat(
                    splashScreenViewProvider.view,
                    View.TRANSLATION_X,
                    0f,
                    -splashScreenViewProvider.view.width.toFloat()
                ).apply {
                    interpolator = DecelerateInterpolator()
                    duration = 1000L
                    doOnEnd { splashScreenViewProvider.remove() }
                }

                slideBack.start()
            }

            setKeepOnScreenCondition { !viewModel.isReady() }
        }

        super.onCreate(savedInstanceState)

        if (viewModel.isFirstLaunch()) {
            startActivity(Intent(applicationContext, Intro::class.java))
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        Log.e("onCreate : ", "before MainActivity setContentView()")
        setContentView(R.layout.activity_main)
        Log.e("onCreate : ", "after MainActivity setContentView()")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = when (destination.id) {
                R.id.homeFragment -> View.VISIBLE
                R.id.historyFragment -> View.VISIBLE
                R.id.cartFragment -> View.VISIBLE
                R.id.profileFragment -> View.VISIBLE
                else -> View.GONE
            }
        }

        bottomNavigationView.setupWithNavController(navController)

        /**
         * This will do the following:
         * Show a title in the app bar based off of the destination's label,
         * and display the Up button whenever you're not on a top-level destination.
         */
//        setSupportActionBar()
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        setupActionBarWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        /** Sign-in with email */
        val currentUser: FirebaseUser? = mAuth.currentUser

        /** Google Sign-In */
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (currentUser == null && account == null) {

            val graph = navController.navInflater.inflate(R.navigation.navigation)
            graph.setStartDestination(R.id.loginFragment)

            navController.graph = graph
        }
        Log.e("onStart : ", currentUser?.email ?: "currentUser is null")
    }

}