package com.wenubey.geoinfo.ui.country.detail.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry
import com.wenubey.geoinfo.utils.formatWithCommasForArea

@Composable
fun GeographicalInformation(country: Country) {
    InfoContent(country = country)
}

@Composable
private fun InfoContent(
    country: Country = fakeCountry
) {
    Column {
        CountryInfoHeader(header = stringResource(id= R.string.geographical_information))
        CountryInfoRow(
            header = stringResource(id= R.string.area),
            imageVector = Icons.Default.Fullscreen,
            contentDescription = stringResource(id= R.string.area_content_description),
            content = country.area.formatWithCommasForArea(),
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.continents),
            imageVector = Icons.Default.SouthAmerica,
            contentDescription = stringResource(id= R.string.country_continents_content_description),
            content = "${country.continents?.joinToString(", ")}"
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.region),
            imageVector = Icons.Default.Explore,
            contentDescription = stringResource(id= R.string.country_region_content_description),
            content = country.region ?: stringResource(id = R.string.undefined),
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.subregion),
            imageVector = Icons.Outlined.MyLocation,
            contentDescription = stringResource(id= R.string.country_subregion_content_description),
            content = country.subRegion ?: stringResource(id = R.string.undefined),
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

