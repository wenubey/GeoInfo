package com.wenubey.geoinfo.ui.country.list

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry
import com.wenubey.geoinfo.utils.formatWithCommasForArea
import com.wenubey.geoinfo.utils.formatWithCommasForPopulation

@Composable
fun CountryListCard(
    country: Country,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit,
    onFavButtonClicked: (country: Country, countryUpdatedFav: Boolean) -> Unit
) {
    CardContent(
        country = country,
        onCardClick = onCardClick,
        onFavButtonClicked = onFavButtonClicked
    )
}

@Composable
private fun CardContent(
    country: Country = fakeCountry,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    onFavButtonClicked: (country: Country, countryUpdatedFav: Boolean) -> Unit = { _, _ -> },
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    onCardClick(country.countryCodeCCA2, country.countryCommonName)
                }
        ) {
            CountryFlag(country = country)
            CountryInfoColumn(country = country, onFavButtonClicked = onFavButtonClicked)
        }
    }
}

@Composable
private fun CountryFlag(
    country: Country
) {
    val painter = rememberAsyncImagePainter(
        model = country.flag?.get("png"),
        error = rememberVectorPainter(image = Icons.Filled.SentimentVeryDissatisfied),
        placeholder = rememberVectorPainter(image = Icons.Filled.SentimentDissatisfied),
        contentScale = ContentScale.Fit
    )
    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp
    val screenWidth = localConfig.screenWidthDp

    Image(
        modifier = Modifier
            .size(
                height = (screenHeight * 0.15).dp,
                width = (screenWidth * 0.30).dp,
            ),
        painter = painter,
        contentDescription = stringResource(id= R.string.country_flag_content_description)
    )
}

@Composable
private fun CountryInfoColumn(
    country: Country,
    onFavButtonClicked: (country: Country, countryUpdatedFav: Boolean) -> Unit
) {
    var isFav by remember {
        mutableStateOf(country.isFavorite)
    }
    LaunchedEffect(country.isFavorite) {
        isFav = country.isFavorite
    }
    Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(0.8f),
                text = country.countryCommonName ?: stringResource(id = R.string.undefined),
                fontSize = 24.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
            )
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.2f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            isFav = !isFav
                            onFavButtonClicked(country, isFav)
                        },
                    ),
                imageVector = if (isFav) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = stringResource(id= R.string.add_fav_content_description)
            )
        }

        Text(
            text = country.capital?.first() ?: stringResource(id = R.string.undefined),
            style = MaterialTheme.typography.bodySmall
        )
        AreaPopulationRow(country = country)
    }
}

@Composable
private fun AreaPopulationRow(
    country: Country
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Icon(
                imageVector = Icons.Filled.People,
                contentDescription = stringResource(id= R.string.population_content_description)
            )
            Text(
                text = country.population.formatWithCommasForPopulation(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Fullscreen,
                contentDescription = stringResource(id= R.string.area_content_description)
            )
            Text(
                text = country.area.formatWithCommasForArea(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountryListCardContentPreview() {
    GeoInfoAppTheme {
        Surface {
            CardContent()
        }
    }
}

