package com.wenubey.countryapp.ui.country.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.detail.components.BasicInformation
import com.wenubey.countryapp.ui.country.detail.components.CulturalInformation
import com.wenubey.countryapp.ui.country.detail.components.EconomicInformation
import com.wenubey.countryapp.ui.country.detail.components.GeographicalInformation
import com.wenubey.countryapp.ui.country.detail.components.TranslationsInformation
import com.wenubey.countryapp.utils.Constants

@Composable
fun CountryDetailContent(
    country: Country,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    painter: AsyncImagePainter,
    languages: Map<String, String>,
    navigateToMapScreen: (countryName: String?) -> Unit
) {

    val localConfig = LocalConfiguration.current

    val screenWidth = localConfig.screenWidthDp
    val screeHeight = localConfig.screenHeightDp

    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(4.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(
                        width = screenWidth.dp,
                        height = (screeHeight / 3).dp
                    ),
                    contentScale = ContentScale.Fit,
                    painter = painter,
                    contentDescription = Constants.COUNTRY_FLAG_CONTENT_DESCRIPTION,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = country.countryCommonName ?: Constants.UNDEFINED,
                        fontSize = 36.sp
                    )
                    IconButton(onClick = { navigateToMapScreen(country.countryCommonName) }) {
                        Icon(
                            imageVector = Icons.Default.PinDrop,
                            contentDescription = Constants.COUNTRY_MAP_CONTENT_DESCRIPTION
                        )
                    }
                }
                Divider(thickness = 1.dp)
            }
        }
        item {
            BasicInformation(country = country)
            GeographicalInformation(country = country)
            CulturalInformation(country = country)
            EconomicInformation(country = country)
            TranslationsInformation(country = country, languages = languages)
        }

    }
}

@Composable
fun InfoHeader(
    content: String
) {
    Text(
        text = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )

}