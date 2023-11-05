package com.wenubey.countryapp.utils

import android.os.Build
import android.os.Build.VERSION_CODES
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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