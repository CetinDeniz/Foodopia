package com.axuca.foodorder.model

import com.google.firebase.database.PropertyName

data class User(
    @get:PropertyName("name_surname") // @set:PropertyName("fieldName")
    val nameSurName: String,
    val email: String,
    val password: String
)