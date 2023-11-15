package com.wenubey.countryapp.domain.model

import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class User(
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String? = null,
    val emailVerified: Boolean? = null,
)

fun FirebaseUser.toUser(): User {
    val currentTime = Calendar.getInstance().time
    val pattern = "dd/MM/yyyy - HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val formattedDate = formatter.format(currentTime)
    return User(
        displayName = displayName,
        email = email,
        photoUrl = photoUrl.toString(),
        phoneNumber = phoneNumber,
        createdAt = formattedDate,
        emailVerified = isEmailVerified
    )
}
