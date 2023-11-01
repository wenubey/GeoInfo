package com.wenubey.countryapp.utils

import android.location.Geocoder
import android.os.Build

@Suppress("DEPRECATION")
fun Geocoder.getCountryName(
    latitude: Double,
    longitude: Double,
    address: (countryName: String?) -> Unit
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()?.countryName) }
        return
    }

    try {
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull()?.countryName)
    } catch(e: Exception) {
        //will catch if there is an internet problem
        address(null)
    }
}