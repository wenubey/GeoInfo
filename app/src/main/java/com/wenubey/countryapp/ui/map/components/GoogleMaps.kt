package com.wenubey.countryapp.ui.map.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.wenubey.countryapp.di.provideGeocoder
import com.wenubey.countryapp.utils.getCountryNameFromLatLng
import com.wenubey.countryapp.utils.getLatLngFromCountryName
import java.util.Locale

@Composable
fun GoogleMaps(
    onMapClick: (countryName: String?) -> Unit,
) {
    val context = LocalContext.current
    val currentCountry = Locale.getDefault().country
    val geocoder = provideGeocoder(context)
    val cameraPosition = geocoder.getLatLngFromCountryName(currentCountry)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPosition, 10f)
    }



    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            geocoder.getCountryNameFromLatLng(latLng.latitude, latLng.longitude) { countryName ->
                onMapClick(countryName)
            }
        },
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
    ) {

    }
}


