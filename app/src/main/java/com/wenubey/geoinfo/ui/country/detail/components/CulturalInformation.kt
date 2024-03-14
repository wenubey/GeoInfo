package com.wenubey.geoinfo.ui.country.detail.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry

@Composable
fun CulturalInformation(country: Country) {
    InfoContent(country = country)
}

@Composable
private fun InfoContent(country: Country = fakeCountry) {
    Column {
        CountryInfoHeader(header =stringResource(id= R.string.cultural_information))
        CountryInfoRow(
            header = stringResource(id= R.string.languages),
            imageVector = Icons.Default.Translate,
            contentDescription = stringResource(id= R.string.country_languages_content_description),
            content = country.language.values.joinToString("\n")
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.timezones),
            imageVector = Icons.Default.Alarm,
            contentDescription = stringResource(id= R.string.country_timezones_content_description),
            content = country.timezones?.joinToString("\n") ?: stringResource(id = R.string.undefined),
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun InfoContentPreview() {
     GeoInfoAppTheme {
        Surface {
             InfoContent()
        }
    }
}