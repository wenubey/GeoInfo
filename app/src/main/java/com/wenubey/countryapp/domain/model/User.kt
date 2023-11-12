package com.wenubey.countryapp.domain.model

import com.google.firebase.auth.FirebaseUser
import java.util.Calendar

data class User(
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String? = null,
    val isEmailVerified: Boolean? = null,
)

fun FirebaseUser.toUser(): User {
    val currentTime = Calendar.getInstance().time
    return User(
        displayName = displayName,
        email = email,
        photoUrl = photoUrl.toString(),
        phoneNumber = phoneNumber,
        createdAt = currentTime.toString(),
        isEmailVerified = isEmailVerified
    )
}
