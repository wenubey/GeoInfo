package com.wenubey.countryapp.utils

import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.countryapp.domain.model.toUser
import com.wenubey.countryapp.utils.Constants.TAG
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun parseDate(day: String?, month: String?, year: String?): Date? {
    val dateString = "$month/$day/$year"
    return if (Build.VERSION.SDK_INT > VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val localDate = LocalDate.parse(dateString, formatter)
        Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    } else {
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(dateString)
    }
}

fun addUserToFirestore(
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    authProvider: AuthProvider,
    createdAt: String? = null
) {
    auth.currentUser?.apply {
        val user = toUser(authProvider, createdAt = createdAt)
        db.collection(Constants.USERS).document(uid).set(user)
    }
}

class Utils {
    companion object {
        fun printLog(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun Context.makeToast(message: String?) =
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()


    }
}

fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time
    val pattern = "dd/MM/yyyy - HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(currentTime)
}

enum class AuthProvider {
    EMAIL,
    GOOGLE,
    FACEBOOK,
    TWITTER
}