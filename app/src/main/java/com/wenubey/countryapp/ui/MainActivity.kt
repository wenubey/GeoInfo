package com.wenubey.countryapp.ui

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryAppTheme {
                val geocoder = Geocoder(this, Locale.getDefault())
                GoogleMaps(geocoder = geocoder)
            }
        }
    }
}



@Composable
fun GoogleMaps(geocoder: Geocoder) {

    val warsaw = LatLng(52.237049, 21.017532)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(warsaw, 10f)
    }


    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            geocoder.getCountryName(latLng.latitude,latLng.longitude) { countryName ->
                if (countryName != null) {
                    Log.i("TAG", "GoogleMaps: Country Name: $countryName")
                }
            }
        }
    ) {

    }
}


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


