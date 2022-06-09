package com.axuca.foodorder.model.firebase

import com.google.firebase.database.PropertyName

/** Firebase User Model */
data class User(
    @get:PropertyName("name_surname") // @set:PropertyName("fieldName")
    val nameSurName: String,
    val email: String,
    val password: String
)