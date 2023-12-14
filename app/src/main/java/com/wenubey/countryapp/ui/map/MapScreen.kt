package com.wenubey.countryapp.ui.map

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.map.components.GoogleMaps
import com.wenubey.countryapp.ui.theme.CountryAppTheme

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
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit = { _, _ ->},
    countryName: String = "",
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        GoogleMaps(
            onMapClick = { countryName, countryCode ->
                navigateToCountryDetailScreen(countryCode, countryName)
            },
            currentCountryName = countryName,
        )
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

