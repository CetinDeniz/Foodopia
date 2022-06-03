package com.axuca.foodorder.util

import com.axuca.foodorder.R
import com.axuca.foodorder.model.Restaurant


fun createRestaurants(): List<Restaurant> {
    return listOf(
        Restaurant(
            0,
            "Neones Burger",
            R.drawable.burger,
            "11:30",
            "23:30",
            "40",
            100,
            9.1f,
            145,
            locationLatitude = 38.45492740738406,
            locationLongitude = 27.118251913230647
        ),
        Restaurant(
            0,
            "Miss Döner",
            R.drawable.iskender,
            "12:00",
            "23:59",
            "45",
            100,
            7.7f,
            300,
            locationLatitude = 38.45957568537859,
            locationLongitude = 27.11429115482801
        ),
        Restaurant(
            0,
            "Çatal'da Tavuk",
            R.drawable.chicken,
            "11:00",
            "23:45",
            "45",
            80,
            8.9f,
            200,
            locationLatitude = 38.45897340282451,
            locationLongitude = 27.117123575023847
        ),
        Restaurant(
            0,
            "Ağam Baklavaları",
            R.drawable.baklava,
            "11:45",
            "23:00",
            "35",
            150,
            9.3f,
            240,
            locationLatitude = 38.49551530526208,
            locationLongitude = 27.045063497765412
        ),
        Restaurant(
            0,
            "Gn Burger",
            R.drawable.burger_3,
            "11:30",
            "23:00",
            "40",
            80,
            8.7f,
            260,
            locationLatitude = 38.49377003219039,
            locationLongitude = 27.047853113231355
        ),
        Restaurant(
            0,
            "Kardeşler Pide & Kebap",
            R.drawable.lahmacun,
            "09:00",
            "23:45",
            "30",
            30,
            8.7f,
            100,
            locationLatitude = 38.492238173399684,
            locationLongitude = 27.080849079952788
        ),
        Restaurant(
            0,
            "Steak Palace",
            R.drawable.meat,
            "11:30",
            "23:00",
            "50",
            175,
            8.6f,
            75,
            locationLatitude = 38.752345,
            locationLongitude = 27.106098
        ),
        Restaurant(
            0,
            "Bart Burger",
            R.drawable.burger_2,
            "11:00",
            "23:59",
            "45",
            150,
            9.1f,
            150,
            locationLatitude = 38.51008471992791,
            locationLongitude = 27.038027777041197
        ),
        Restaurant(
            0,
            "Lezzet-i Ala",
            R.drawable.meatball,
            "10:00",
            "23:30",
            "35",
            150,
            8.7f,
            220,
            locationLatitude = 38.488207764024885,
            locationLongitude = 27.060923001768963
        ),
        Restaurant(
            0,
            "Ora",
            R.drawable.pide,
            "10:45",
            "21:45",
            "30",
            90,
            9.2f,
            365,
            locationLatitude = 38.486987194266135,
            locationLongitude = 27.08827419510647
        ),
        Restaurant(
            0,
            "Sicilia Pizzeria",
            R.drawable.pizza,
            "12:00",
            "23:00",
            "45",
            90,
            8.5f,
            250,
            locationLatitude = 38.49730177762566,
            locationLongitude = 27.049184453982352
        ),
        Restaurant(
            0,
            "Domino's Pizza",
            R.drawable.pizza_2,
            "10:00",
            "23:59",
            "30",
            25,
            8.2f,
            350,
            locationLatitude = 38.460478573370686,
            locationLongitude = 27.12633615398235
        ),
        Restaurant(
            0,
            "Nappo",
            R.drawable.pizza_3,
            "11:30",
            "22:30",
            "20",
            30,
            8.2f,
            175,
            locationLatitude = 38.47369966250417,
            locationLongitude = 27.075061184660786
        ),
        Restaurant(
            0,
            "Arya Balık & Meze Evi",
            R.drawable.salmon,
            "11:30",
            "22:30",
            "40",
            200,
            9.3f,
            400,
            locationLatitude = 38.473797869350705,
            locationLongitude = 27.1016802
        )
    )
}