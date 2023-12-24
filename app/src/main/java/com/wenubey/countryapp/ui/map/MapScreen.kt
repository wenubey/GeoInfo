package com.wenubey.countryapp.ui.map

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.CountryEvent
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun MapScreen(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit,
    countryName: String,
) {
    MapScreenContent(
        navigateToCountryDetailScreen = navigateToCountryDetailScreen,
        countryName = countryName,
    )
}

@Composable
private fun MapScreenContent(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    countryName: String = "",
    countryViewModel: CountryViewModel = koinViewModel(),
) {
    countryViewModel.onEvent(CountryEvent.OnGetAllFavoriteCountries)
    Column(
        Modifier
            .fillMaxSize()
    ) {
        GoogleMaps(
            onMapClick = { countryName, countryCode ->
                navigateToCountryDetailScreen(countryCode, countryName)
            },
            currentCountryName = countryName,
            favCountriesLatLng = countryViewModel.favCountriesLatLng
        )
    }
}

@Composable
private fun GoogleMaps(
    onMapClick: (countryName: String?, countryCode: String?) -> Unit,
    currentCountryName: String,
    favCountriesLatLng: List<LatLng>
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        runBlocking {
            position = CameraPosition.fromLatLngZoom(
                getLatLngFromCountryNameSuspend(
                    context,
                    currentCountryName
                ), 6f
            )
        }
    }
    val scope = rememberCoroutineScope()
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            scope.launch {
                val pickedCountry = getCountryNameFromLatLng(
                    context = context,
                    latitude = latLng.latitude,
                    longitude = latLng.longitude
                )
                val countryCode = pickedCountry?.first
                val countryName = pickedCountry?.second
                onMapClick(countryName, countryCode)
            }
        },
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
    ) {
        favCountriesLatLng.forEach {
            MarkerComposable(state = MarkerState(it)) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color.Yellow)
            }
        }

    }
}



@Suppress("DEPRECATION")
private suspend fun getCountryNameFromLatLng(
    context: Context,
    latitude: Double,
    longitude: Double
): Pair<String?, String?>? {
    return withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                return@withContext Pair<String?, String?>(
                    addresses[0].countryCode,
                    addresses[0].countryName
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}

@Suppress("DEPRECATION")
private suspend fun getLatLngFromCountryNameSuspend(context: Context, countryName: String): LatLng {
    return withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val address = geocoder.getFromLocationName(countryName, 1)?.first()
                val latitude = address?.latitude ?: 0.0
                val longitude = address?.longitude ?: 0.0
                LatLng(latitude, longitude)
            } else {
                val address = geocoder.getFromLocationName(countryName, 1)?.first()
                val latitude = address?.latitude ?: 0.0
                val longitude = address?.longitude ?: 0.0
                LatLng(latitude, longitude)
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "getLatLngFromCountryName error: $e")
            LatLng(0.0, 0.0)
        }
    }
}


@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun MapScreenContentPreview() {
    CountryAppTheme {
        Surface {
            MapScreenContent()
        }
    }
}

