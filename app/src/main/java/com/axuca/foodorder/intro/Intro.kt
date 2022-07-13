package com.axuca.foodorder.intro

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.axuca.foodorder.view.MainActivity
import com.axuca.foodorder.R
import com.axuca.foodorder.viewmodel.IntroVM
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Intro : AppIntro2() {

    private val viewModel by viewModels<IntroVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Dot or Progress */
        isIndicatorEnabled = true
        setIndicatorColor(
            selectedIndicatorColor = getColor(android.R.color.holo_red_dark),
            unselectedIndicatorColor = getColor(R.color.black)
        )

        /** Fullscreen mode
         * This will hide both the Status Bar and the Navigation bar */
        setImmersiveMode()

        /** System back button lock */
        isSystemBackButtonLocked = true

        /** Slide screens */
        val colorRes = getTextColor()

        addSlide(
            AppIntroFragment.createInstance(
                title = "Online Food Order",
                description = "Choose from a wide range of delicious foods and drinks.", // meals
                imageDrawable = R.drawable.ic_order_food_pana,
                titleColorRes = colorRes,
                descriptionColorRes = colorRes
            )
        )

        addSlide(
            AppIntroFragment.createInstance(
                title = "Discover Place Near You",
                description = "We make it simple to find the food you crave.Accept permission and let us do the rest.",
                imageDrawable = R.drawable.ic_location_search_rafiki,
//                backgroundDrawable = backgroundDrawable, /** Might be a gradient */
                titleColorRes = colorRes,
                descriptionColorRes = colorRes
//                backgroundColorRes = R.color.white /** Single color for background */
//                titleTypefaceFontRes = R.font.opensans_regular,
//                descriptionTypefaceFontRes = R.font.opensans_regular
            )
        )

        addSlide(
            AppIntroFragment.createInstance(
                title = "Fastest Food Delivery",
                description = "Your order will be immediately collected and sent by our best and fastest food delivery courier.",
                imageDrawable = R.drawable.ic_take_away_amico,
                titleColorRes = colorRes,
                descriptionColorRes = colorRes,
            )
        )

        /** Permissions */
        askForPermissions(
            permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            slideNumber = 2,
            required = false
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        appIntroEndingProcess()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        appIntroEndingProcess()
    }

    private fun appIntroEndingProcess() {
        viewModel.endingProcess()
        startActivity(Intent(this@Intro, MainActivity::class.java))
        finish()
    }

    private fun getTextColor(): Int {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> { // Night mode is not active, we're using the light theme
                // Toast.makeText(this, "Light Mode", Toast.LENGTH_SHORT).show()
                R.color.black
            }
            Configuration.UI_MODE_NIGHT_YES -> { // Night mode is active, we're using dark theme
                // Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show()
                R.color.white
            }
            else -> {
                throw Exception("UI Mode couldn't detect!")
            }
        }
    }

}