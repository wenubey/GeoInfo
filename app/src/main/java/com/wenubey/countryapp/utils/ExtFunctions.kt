package com.wenubey.countryapp.utils

import android.location.Geocoder
import android.os.Build
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.wenubey.countryapp.utils.Constants.TAG

@Suppress("DEPRECATION")
fun Geocoder.getCountryNameFromLatLng(
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
    } catch (e: Exception) {
        Log.e(TAG, "getCountryNameFromLatLng error: $e", )
        address(null)
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
    try {
        val address = getFromLocationName(countryName, 1)?.first()
        val latitude = address!!.latitude
        val longitude = address.longitude
        return LatLng(latitude, longitude)
    } catch (e: Exception) {
        Log.e(TAG, "getLatLngFromCountryName error: $e", )
        return LatLng(0.0, 0.0)
    }
}