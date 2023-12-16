package com.wenubey.countryapp.ui.country.search_bar

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.Constants.AREA_CONTENT_DESCRIPTION
import com.wenubey.countryapp.utils.Constants.POPULATION_CONTENT_DESCRIPTION
import com.wenubey.countryapp.utils.Constants.UNDEFINED
import com.wenubey.countryapp.utils.fakeCountry
import com.wenubey.countryapp.utils.formatWithCommasForArea
import com.wenubey.countryapp.utils.formatWithCommasForPopulation

@Composable
fun CountrySearchResultCard(
    country: Country,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit
) {
    CardContent(
        country = country,
        onCardClick = onCardClick
    )
}

@Composable
private fun CardContent(
    country: Country = fakeCountry,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit = { _, _ ->}
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
            CountryInfoColumn(country = country)
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
                height = (screenHeight * 0.1).dp,
                width = (screenWidth * 0.25).dp,
            ),
        painter = painter,
        contentDescription = Constants.COUNTRY_FLAG_CONTENT_DESCRIPTION
    )
}

@Composable
private fun CountryInfoColumn(
    country: Country
) {
    Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.Start) {
        Text(
            text = country.countryCommonName ?: UNDEFINED,
            fontSize = 24.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(text = country.capital?.first() ?: UNDEFINED, fontSize = 14.sp)
        AreaPopulationRow(country = country)
    }
}

@Composable
private fun AreaPopulationRow(
    country: Country
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Icon(
                imageVector = Icons.Filled.People,
                contentDescription = POPULATION_CONTENT_DESCRIPTION
            )
            Text(text = country.population.formatWithCommasForPopulation())
        }
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Icon(
                imageVector = Icons.Filled.Fullscreen,
                contentDescription = AREA_CONTENT_DESCRIPTION
            )
            Text(text = country.area.formatWithCommasForArea(), overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }

}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountrySearchResultCardPreview() {
     CountryAppTheme {
        Surface {
            CardContent()
        }
    }
}

