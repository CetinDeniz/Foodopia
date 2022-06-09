package com.axuca.foodorder

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.axuca.foodorder.databinding.ActivityMainBinding
import com.axuca.foodorder.intro.Intro
import com.axuca.foodorder.viewmodel.MainVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainVM>()
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.visibility = when (destination.id) {
                R.id.homeFragment -> View.VISIBLE
                R.id.historyFragment -> View.VISIBLE
                R.id.cartFragment -> View.VISIBLE
                R.id.profileFragment -> View.VISIBLE
                else -> View.GONE
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        /** Sign-in with email */
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        /** Google Sign-In */
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (currentUser == null && account == null) {
            val graph = navController.navInflater.inflate(R.navigation.navigation)
            graph.setStartDestination(R.id.loginFragment)

            navController.graph = graph
        }

        viewModel.setReady()
    }
}