package com.wenubey.countryapp.ui.country.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.detail.components.BasicInformation
import com.wenubey.countryapp.ui.country.detail.components.CulturalInformation
import com.wenubey.countryapp.ui.country.detail.components.EconomicInformation
import com.wenubey.countryapp.ui.country.detail.components.FlagWithCountryCommonName
import com.wenubey.countryapp.ui.country.detail.components.GeographicalInformation
import com.wenubey.countryapp.ui.country.detail.components.HistoricalInformation
import com.wenubey.countryapp.ui.country.detail.components.TranslationsInformation

@Composable
fun CountryDetailContent(
    country: Country,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    languages: Map<String, String>,
    navigateToMapScreen: (countryName: String?) -> Unit
) {


    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(4.dp)
    ) {
        item {
            FlagWithCountryCommonName(
                country = country,
                navigateToMapScreen = navigateToMapScreen
            )
        }
        item {
            BasicInformation(country = country)
            GeographicalInformation(country = country)
            CulturalInformation(country = country)
            EconomicInformation(country = country)
            HistoricalInformation(histories = country.history ?: emptyList())
            TranslationsInformation(country = country, languages = languages)
        }
    }
}




