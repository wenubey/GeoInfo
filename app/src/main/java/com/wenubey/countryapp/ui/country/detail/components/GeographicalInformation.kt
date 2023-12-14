package com.wenubey.countryapp.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.SouthAmerica
import androidx.compose.material.icons.outlined.MyLocation
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
import com.wenubey.countryapp.utils.formatWithCommasForArea

@Composable
fun GeographicalInformation(country: Country) {
    InfoContent(country = country)
}

@Composable
private fun InfoContent(
    country: Country = fakeCountry
) {
    Column {
        InfoHeader(header = Constants.GEOGRAPHICAL_INFORMATION)
        CountryInfoRow(
            header = Constants.AREA,
            imageVector = Icons.Default.Fullscreen,
            contentDescription = Constants.AREA_CONTENT_DESCRIPTION,
            content = country.area.formatWithCommasForArea(),
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = Constants.CONTINENTS,
            imageVector = Icons.Default.SouthAmerica,
            contentDescription = Constants.COUNTRY_CONTINENTS_CONTENT_DESCRIPTION,
            content = "${country.continents?.joinToString(", ")}"
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = Constants.REGION,
            imageVector = Icons.Default.Explore,
            contentDescription = Constants.COUNTRY_REGION_CONTENT_DESCRIPTION,
            content = country.region ?: Constants.UNDEFINED,
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = Constants.SUBREGION,
            imageVector = Icons.Outlined.MyLocation,
            contentDescription = Constants.COUNTRY_SUBREGION_CONTENT_DESCRIPTION,
            content = country.subRegion ?: Constants.UNDEFINED,
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

