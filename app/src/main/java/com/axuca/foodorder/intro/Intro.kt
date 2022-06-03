package com.axuca.foodorder.intro

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.axuca.foodorder.MainActivity
import com.axuca.foodorder.R
import com.axuca.foodorder.db.RestaurantDatabase
import com.axuca.foodorder.injection.firstLaunchKey
import com.axuca.foodorder.util.createRestaurants
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Intro : AppIntro2() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("onCreate : ", "Intro Slider Start")
        super.onCreate(savedInstanceState)

        /** Dot or Progress */
        isIndicatorEnabled = true

        // Change Indicator Color
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
                // We make food ordering fast,simple and free - not matter if you order online or cash.
                // Enjoy instant delivery and payment!
                imageDrawable = R.drawable.ic_take_away_amico, // ic_delivery_amico
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

        Log.e("onCreate : ", "Intro Slider End")
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)

        addRestaurantsToDatabase()
        startMainActivity()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        addRestaurantsToDatabase()
        startMainActivity()
    }


    private fun addRestaurantsToDatabase(){
        lifecycleScope.launch(Dispatchers.IO) {
            RestaurantDatabase
                .getDatabase(this@Intro)
                .getRestaurantDao()
                .addRestaurants(createRestaurants())
        }
    }

    private fun startMainActivity() {
        lifecycleScope.launch {
            dataStore.edit {
                it[firstLaunchKey] = false
            }
            finish()
            startActivity(Intent(this@Intro, MainActivity::class.java))
        }
    }

    private fun getTextColor(): Int {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> { // Night mode is not active, we're using the light theme
                Toast.makeText(this, "Light Mode", Toast.LENGTH_SHORT).show()
                R.color.black
            }
            Configuration.UI_MODE_NIGHT_YES -> { // Night mode is active, we're using dark theme
                Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show()
                R.color.white
            }
            else -> {
                throw Exception("UI Mode couldn't detect!")
            }
        }
    }

}