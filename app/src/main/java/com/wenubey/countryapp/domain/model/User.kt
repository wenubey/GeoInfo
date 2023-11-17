package com.wenubey.countryapp.domain.model

import com.google.firebase.auth.FirebaseUser
import com.wenubey.countryapp.utils.AuthProvider

data class User(
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String? = null,
    val emailVerified: Boolean? = null,
    val authProvider: AuthProvider? = null,
)

fun FirebaseUser.toUser(authProvider: AuthProvider?, createdAt: String? = null): User {
    return User(
        displayName = displayName,
        email = email,
        photoUrl = photoUrl.toString(),
        phoneNumber = phoneNumber,
        createdAt = createdAt,
        emailVerified = isEmailVerified,
        authProvider = authProvider
    )
}


