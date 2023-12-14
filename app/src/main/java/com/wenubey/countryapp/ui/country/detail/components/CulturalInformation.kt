package com.wenubey.countryapp.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.fakeCountry

@Composable
fun CulturalInformation(country: Country) {
    InfoContent(country = country)
}

@Composable
private fun InfoContent(country: Country = fakeCountry) {
    Column {
        InfoHeader(header = Constants.CULTURAL_INFORMATION)
        CountryInfoRow(
            header = Constants.LANGUAGES,
            imageVector = Icons.Default.Translate,
            contentDescription = Constants.COUNTRY_LANGUAGES_CONTENT_DESCRIPTION,
            content = country.language.values.joinToString("\n")
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = Constants.TIMEZONES,
            imageVector = Icons.Default.Alarm,
            contentDescription = Constants.COUNTRY_TIMEZONES_CONTENT_DESCRIPTION,
            content = country.timezones?.joinToString("\n") ?: Constants.UNDEFINED,
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun InfoContentPreview() {
     CountryAppTheme {
        Surface {
             InfoContent()
        }
    }
}