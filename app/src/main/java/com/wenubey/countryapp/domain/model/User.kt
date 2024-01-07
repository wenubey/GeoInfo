package com.wenubey.countryapp.domain.model

import com.google.firebase.auth.FirebaseUser
import com.wenubey.countryapp.utils.AuthProvider

data class User(
    val displayName: String? = null,
    val email: String? = null,
    val photoUri: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String? = null,
    val emailVerified: Boolean? = null,
    val authProvider: AuthProvider? = null,
    val isPhoneNumberVerified: Boolean? = false,
    val favCountries: Map<String, String>? = null,
)

fun FirebaseUser.toUser(authProvider: AuthProvider?, createdAt: String? = null, isPhoneNumberVerified: Boolean?, favCountries:Map<String, String>?): User {
    return User(
        displayName = displayName,
        email = email,
        photoUri = photoUrl.toString(),
        phoneNumber = phoneNumber,
        createdAt = createdAt,
        emailVerified = isEmailVerified,
        authProvider = authProvider,
        isPhoneNumberVerified = isPhoneNumberVerified,
        favCountries = favCountries
    )
}


