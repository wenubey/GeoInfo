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
import com.wenubey.geoinfo.utils.Constants.UNDEFINED
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
        InfoHeader(header = stringResource(id= R.string.GEOGRAPHICAL_INFORMATION))
        CountryInfoRow(
            header = stringResource(id= R.string.AREA),
            imageVector = Icons.Default.Fullscreen,
            contentDescription = stringResource(id= R.string.AREA_CONTENT_DESCRIPTION),
            content = country.area.formatWithCommasForArea(),
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.CONTINENTS),
            imageVector = Icons.Default.SouthAmerica,
            contentDescription = stringResource(id= R.string.COUNTRY_CONTINENTS_CONTENT_DESCRIPTION),
            content = "${country.continents?.joinToString(", ")}"
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.REGION),
            imageVector = Icons.Default.Explore,
            contentDescription = stringResource(id= R.string.COUNTRY_REGION_CONTENT_DESCRIPTION),
            content = country.region ?: UNDEFINED,
        )
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        CountryInfoRow(
            header = stringResource(id= R.string.SUBREGION),
            imageVector = Icons.Outlined.MyLocation,
            contentDescription = stringResource(id= R.string.COUNTRY_SUBREGION_CONTENT_DESCRIPTION),
            content = country.subRegion ?: UNDEFINED,
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

