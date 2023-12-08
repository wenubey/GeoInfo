package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.formatWithCommasForPopulation

@Composable
fun BasicInformation(country: Country) {
    InfoHeader(content = Constants.BASIC_INFORMATION)
    CountryInfoRow(
        header = Constants.KNOWN_AS,
        imageVector = Icons.Default.TravelExplore,
        contentDescription = Constants.COUNTRY_KNOWN_AS_CONTENT_DESCRIPTION,
        content = "${country.countryCommonName}\n${country.countryNativeName?.values?.first()?.common}",
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.CAPITAL,
        imageVector = Icons.Filled.Castle,
        contentDescription = Constants.COUNTRY_CAPITAL_CONTENT_DESCRIPTION,
        content = "${country.capital?.joinToString(", ")}",
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.POPULATION,
        imageVector = Icons.Default.People,
        contentDescription = Constants.POPULATION_CONTENT_DESCRIPTION,
        content = country.population.formatWithCommasForPopulation()
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.TOP_LEVEL_DOMAINS,
        imageVector = Icons.Default.Domain,
        contentDescription = Constants.COUNTRY_TOP_LEVEL_DOMAINS_CONTENT_DESCRIPTION,
        content = country.topLevelDomain?.joinToString("\n") ?: Constants.UNDEFINED,
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.COUNTRY_CODE,
        imageVector = Icons.Default.Tag,
        contentDescription = Constants.COUNTRY_CODE_CONTENT_DESCRIPTION,
        content = country.countryCodeCCA2 ?: Constants.UNDEFINED,
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.PHONE_CODE,
        imageVector = Icons.Default.Call,
        contentDescription = Constants.COUNTRY_PHONE_CODE_CONTENT_DESCRIPTION,
        content = country.flagEmojiWithPhoneCode.values.first()
            ?: Constants.UNDEFINED,
    )
}
