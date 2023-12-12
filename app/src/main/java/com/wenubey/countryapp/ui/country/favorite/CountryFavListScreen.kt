package com.wenubey.countryapp.ui.country.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants

@Composable
fun CountryFavListScreen() {
    CountryFavListContent()
}

@Composable
private fun CountryFavListContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = Constants.COUNTRY_FAV_LIST_SCREEN_TITLE)
    }
}


@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CountryFavListContentPreview() {
    CountryAppTheme {
        Surface {
            CountryFavListContent()
        }
    }
}