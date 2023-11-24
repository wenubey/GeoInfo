package com.wenubey.countryapp.utils

import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.gms.maps.model.LatLng
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.Constants.UNDEFINED

@Suppress("DEPRECATION")
fun Geocoder.getCountryNameFromLatLng(
    latitude: Double,
    longitude: Double,
    countryName: (countryName: String?) -> Unit
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { countryName(it.firstOrNull()?.countryName) }
        return
    }

    try {
        countryName(getFromLocation(latitude, longitude, 1)?.firstOrNull()?.countryName)
    } catch (e: Exception) {
        Log.e(TAG, "getCountryNameFromLatLng error: $e")
        countryName(null)
    }
}

@Suppress("DEPRECATION")
fun Geocoder.getLatLngFromCountryName(
    countryName: String
): LatLng {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val address = getFromLocationName(countryName, 1)?.first()
        val latitude = address!!.latitude
        val longitude = address.longitude
        return LatLng(latitude, longitude)
    }
    return try {
        val address = getFromLocationName(countryName, 1)?.first()
        val latitude = address!!.latitude
        val longitude = address.longitude
        LatLng(latitude, longitude)
    } catch (e: Exception) {
        Log.e(TAG, "getLatLngFromCountryName error: $e")
        LatLng(0.0, 0.0)
    }
}

fun Double?.formatWithCommasForArea(): String {
    if (this == null) {
        return UNDEFINED
    }
    val formattedValue = String.format("%,.2f", this)
    return if (formattedValue.endsWith(".00")) {
        formattedValue.substring(0, formattedValue.length - 3) + " km²"
    } else {
        "$formattedValue km²"
    }
}

fun Int?.formatWithCommasForPopulation(): String {
    return if (this == null) {
        UNDEFINED
    } else {
        String.format("%,d", this)
    }
}

fun SortOption.toIcon(): ImageVector {
    return when (this) {
        SortOption.NAME -> Icons.Filled.SortByAlpha
        SortOption.AREA -> Icons.Filled.Fullscreen
        SortOption.POPULATION -> Icons.Filled.People
    }
}